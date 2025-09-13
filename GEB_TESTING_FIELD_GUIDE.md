Geb Testing Field Guide
=======================
This document is meant to cover key things for Geb test development. Whether this is your first time writing a Geb test
or your hundredth time, this document should be a useful summary of key things you need to know to write a good browser
test.

This document assumes you're already familiar with [Spock](https://spockframework.org/). Likewise, this guide isn't
meant to replicate reading [the freaking manual](https://groovy.apache.org/geb/manual/current/).

This guide is meant to cover how to pragmatically use Geb. With it, you too can learn to stop worrying and love the
browser.

# Browser Testing is Hard, Let's Go Mocking

Seriously, before you set out to write a Geb test for a feature, ask yourself if that's really the right thing. You have
other avenues available, right? Unit tests, integration tests, etc. Browser tests, in general take longer to run, are
slower to develop, and require more work to maintain.

Of course, they add some critical value to your testing infrastructure. The key thing is that not every single feature
needs a browser test. Ask yourself, your peers, and the wider team whether what you're testing needs a browser test. If
comparable testing can be accomplished with a unit test, prefer that. If you can do it via an integration test, do that.
You've probably [heard this one before](https://martinfowler.com/articles/practical-test-pyramid.html).

Finally, try to keep your browser test to the minimum you need to test _in the browser_. In practice, that means a few
things. Firstly, that means that you don't have to exhaustively test every validation message in every feature via Geb.
You may wish to test certain things end-to-end, but even a full user journey need not include an exhaustive test of
everything (such as error handling). Secondly, _don't_ use the browser to "set up" the environment that your test
requires. For example, you shouldn't drive through your app's UI to create all the user data your test might presume is
setup as a prerequisite. Use some test fixtures to create that stuff, maybe via your app's REST API, maybe via a
pre-built dataset.

# Things You Should Do

## Use Page Objects and Modules

One of the key things to grasp about prudent use of Geb is
the [Page Object](https://gebish.org/manual/current/#the-page-object-pattern) pattern and the closely
related [Module](https://gebish.org/manual/current/#modules). At base, these two patterns are all about modeling what's
in the user interface in a way that's clearer to read and reuse when writing Geb tests. It may be worth it to create a
module of Page Objects and Modules in your project that can be reused, especially if you have lots of services or
subprojects that need end-to-end tests.

To make sure that your tests aren't flaky, any actions which require waiting (such as clicking on a button that
triggers some asynchronous HTTP requests) need to be encapsulated in one method on a page object or module. That method
should contain the relevant waiting code as well.

Expand this for some code examples

`page.helpLink.click()`

Then, in some other file...

```groovy
class MyPage extends geb.Page {
    static content = {
        helpLink { $('a.help') }
    }
}
```

is easier to read than

`$('a.help').click()`

Likewise,

`page.navigateToHelp()`

Then, in some other file...

```groovy
class MyPage extends geb.Page {

    static content = {
        helpLink { $('a.help') }
    }

    void navigateToHelp() {
        helpLink.click()
        waitFor {
            $('h1#help-title').visible
        }
    }
}
```

is better than

`$('a.help').click()`

```groovy
waitFor {
    $('h1#help-title').visible
}
```

### Expressive Specs, Detailed Modules

The goal should be to write specs that express the intention as close to natural language as possible, while Modules &
Page objects encapsulate the implementation details in a readable, reusable way. For example, say there's a button on
your page that, when clicked, displays a dialog. A good method on the Button's module would look like this:

```groovy
SomePageDialog configure() {
    click()
    def dialog = browser.module(SomePageDialog)
    waitFor { dialog.initialised }
    dialog
}
```

This method contains all the logic to click the button, wait for the dialog to finish opening, and then returns the
dialog that opens. So, in the actual test, the code can be really expressive:

```groovy
def somePageDialog = page.somePageButton.configure()
somePageDialog.clickOk()
```

## Use Existing Fixtures, Page Objects, and Modules before introducing new ones

If there's already a page object, module, or fixture that does what you want, obviously you should use that. In fact, if
there's something that already models the UI element you're dealing with, but doesn't model the behavior you need to
test, [consider extending, modifying, or improving that before you introduce redundancy](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself).

### Caveat: Reuse, not Reabuse

Some UIs are complex. Be careful not to press unrelated modules into service to represent things they don't.

## Investigate Test Failures

If a test fails once for you locally and it's not clear to you why, it's worth paying attention. Even if it passes on
rerun, if you don't have an explanation for the failure that makes you confident it won't happen again, you've probably
got a flaky test on your hands.

In addition to forcing network latency, there are other ways you can suss out flakiness without wasting your time.

### Using Reporters and Failure Reports

If you run your test from the command line, you'll get some helpful test reports in the browser test module's build
directory. For example, if you run `workshop.OrganizedTestsSpec`...

`./gradlew test --tests "workshop.OrganizedTestsSpec"`

...then you should have an output directory at build/reports/tests/geb-reports/ for any failures.

Assuming you've configured the appropriate reporters, you should find four snapshots that can be useful for different
scenarios.

* A .png file which is a screenshot of the browser at the time of failure
* An HTML file which contains the complete DOM for the page at the time of failure
    * Pro-tip: Opening this in your favorite editor and using the autoformatter can make this a lot more readable.

Note that you can use custom reporters to include things like:

* A thread dump of the JVM from your host application
* Logs from your host application

### Forcing failure with rapid reruns

A quick way to help verify that your test isn't flaky is to run a bunch of iterations at once. Doing this manually is a
pain, but there are programmatic ways to run the test a whole bunch. One way to do this is to use Spock's `where` clause.
For example, if you've got a test called `"test feature x"` , you could modify it like so to make it run 20 iterations in
rapid succession:

```groovy
def "test feature x"() {
//your normal test body is here
    where:
    number << (1..20)
}
```

If even one of those runs fails, that means your test will probably fail about 5% of the time. Since Spock test iterations are unrolled by default, you'll get distinct screenshots & HTML snapshots for every single test failure,
which can help a lot when debugging.

Take care that you don't _commit_ the where clause described above. Your test will get executed
multiple times daily on your CI server every time someone runs the build. There's no need to slow the CI build down
permanently. This is a good tactic _while you are working on the test_, not one to permanently enshrine in the company's
AWS build & developer wait times. Become good friends with `git stash` and `git reset` to quickly back out changes like
this. `git add -p` is also handy for selectively staging/committing stuff.

## Induce browser latency to catch flaky tests before they are merged

Try introducing [network latency](https://groovy.apache.org/manual/current/#controlling-network-conditions) to see if your
test will work if HTTP requests are slow to come back. This allows the test to run in a slower environment which will
identify what's _not_ loading properly. You can do so by adding a few lines to the test setup:

```groovy
import java.time.Duration

class MyTest extends GebReportingSpec {

    def "test something"() {
        setup:
        def networkLatency = Duration.ofMillis(300)
        browser.networkLatency = networkLatency
        //...your test code goes here

    }
}
```

You can do the same thing in the `setup` and `setupSpec` methods on a whole test class to run several tests with latency.

Unless you have a valid test case with network latency that you want to run _every time someone runs the build_, *
*remove this code block before merging.**

### Latency on CI

Additionally, it may behoove you to create a CI plan that runs all of your tests with latency cranked up. You don't necessarily need to wait for the plan to complete before opening a PR (it will take a long time). If
you've used the above latency code on the test you're working on, you're probably fine. However, a latency CI plan
may reveal any accidental flakiness you've introduced into other tests.

## Use fixtures and AutoCleanup

When you're manipulating the backend data to setup the environment that you need to achieve your tests, you're likely
going to want to use a fixture class or two.

Assuming there isn't a fixture already (look hard for one if you've got a well established codebase!), you'll need to create a new fixture. 

First, understanding the data that you're manipulating. Get your head around how to create, edit, and update the
objects appropriately.

Create a class to serve as the fixture for this. Add a `void close` method to your fixture. Then, when you inject it into your Spec, you can annotate it with `@AutoCleanup` from Spock. That method will get invoked when your test finishes, cleaning up any data left behind.

# Things You Should Never Do

## Use Thread.sleep to wait for things

Instead of sleeping for a specified duration, you should use `waitFor` statements to wait for some indication in the DOM
that whatever you're waiting for has completed and that you're ready to proceed onto the next step. This is a way of
making the test say what it really means to test. Browser timings are not the guaranteed behavior we want to test for,
but the document updates _are_.

## Waiting in the Spec

Don't use `waitFor` statements in your actual test methods. Doing so is a good sign that you probably aren't using Page
Objects or Modules for something that you should be (see above). Ask yourself why it is you need to wait. If it's
because some action that you triggered in the line before is waiting for an HTTP response from the server, then you
should probably have a method on the page object or module you're touching which combines the, "Click this thing, then
wait for this other thing to happen" logic into one expressive method.

Caveat: Obviously, what you don't commit & PR is your business. It's okay if you need to do some `waitFor`in the spec while you're in development and hacking around to figure things out, but get that logic encapsulated as soon as
possible.

### What you should do instead: Build Good Page Objects

See the notes about how to Use Page Objects and Modules. Basically, any action that triggers something asynchronous
should probably be in a method on a class that extends geb.Page or geb.Module. That method should wait for the action to
complete.

## Re-running the build to get your test to pass

If your test fails on CI for reasons you don't understand, but passes on a re-run, that's a good sign that it's
flaky and we don't want it in the build as-is.

Likewise, if your changes have caused some _other_ test to become flaky, take a deep look at your use of 
fixtures. It may be that your test isn't cleaning up after itself, or that some changes you need conflict with expected
behavior in another context.

Treat test failures like important warnings that you need to take seriously, not private failures that you need to hide
from the team. Nobody's going to judge you for writing a flaky spec if you are open about it and willing to fix it.

## Needlessly marking selectors as not required

For page objects, it's possible to mark an element as not required. e.g.

```groovy
import geb.Module

class MyModule extends Module {
    static content = {
        requiredButton { $('button.required') }

        notRequiredButton(required: false) { $('button.not-required') }

    }
}
```

You should _only_ use `required: false` when your test needs to be able to access that definition when the element is not
there. For example:

```groovy
def "test that button shows & hides correctly"() {
    when:
    page.doSomethingWhichMayCauseButtonToBeRemovedFromDOM()

    then:
    !page.buttons.notRequiredButton
}
```

If the button may or may not be there, but you never test the case where it isn't there, you should just leave it as
required. That way, the test will throw an informative exception when the element is missing.

As with all these rules, the goal is that test failures are _meaningful_. Marking something as not required when it
actually _is_ required in every case that it's called for will lead to confusing failures down the road.
