# Piano App
Please write a web application for me. The frontend should be written in HTMX (see https://htmx.org/).

The application displays a piano keyboard on the screen along with a blank treble clef and bass clef. When the user clicks a key on the piano, the app plays the note and adds a note to the appropriate place on the clef. When a lot of notes are on the clef, it should scroll. Playing two notes at a time should annotate a cord on the clef, but notes played in succession should go from left to right, as usual.

Users should also be able to use the home keys on their keyboard to play the piano (from a to '). Sharp/flat notes (the black keys) should be played by the upper row of keyboard keys. They should be arranged such that the black key between two white keys corresponds to a letter on the top row of the keyboard between two notes.

Since the piano has more keys than there are letters in the alphabet, users can hold shift to reach the upper registers or hold CTRL to reach the lower ones. 

## Changelog

### Durable notes and mouse interactivity
That's pretty good! A few changes I'd like.

If a user holds down the keys, I'd like the notes to keep playing.

Also, if a user clicks on the key, I'd like the note to play as long as the user is holding the note.
