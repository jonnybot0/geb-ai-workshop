#!/bin/bash

# Geb Workshop Test Runner
# This script provides easy commands to run different test scenarios

set -e

echo "🚀 Geb AI Workshop Test Runner"
echo "================================"

case "${1:-help}" in
    "rough")
        echo "📝 Running rough tests (unorganized)..."
        ./gradlew test --tests="*RoughTestsSpec" -Dgeb.headless=true
        ;;
    
    "organized") 
        echo "🎯 Running organized tests (with page objects)..."
        ./gradlew test --tests="*OrganizedTestsSpec" -Dgeb.headless=true
        ;;
        
    "parallel")
        echo "⚡ Running parallel tests..."
        time ./gradlew parallelTest -Dgeb.headless=true
        ;;
        
    "tdd")
        echo "🔄 Running TDD examples..."
        ./gradlew test --tests="*TDDExampleSpec" -Dgeb.headless=true --continue
        ;;
        
    "grid")
        echo "🌐 Running tests against Selenium Grid..."
        if ! curl -s http://localhost:4444/status > /dev/null; then
            echo "❌ Selenium Grid not running. Starting with Docker..."
            docker-compose up -d
            sleep 10
        fi
        ./gradlew gridTest -Dselenium.grid.url=http://localhost:4444/wd/hub
        ;;
        
    "all")
        echo "🎪 Running all test suites..."
        ./gradlew test -Dgeb.headless=true --continue
        ;;
        
    "clean")
        echo "🧹 Cleaning up..."
        ./gradlew clean
        docker-compose down 2>/dev/null || true
        ;;
        
    "setup")
        echo "⚙️  Setting up workshop environment..."
        ./gradlew build -Dgeb.headless=true
        echo "✅ Setup complete! Try: ./run-tests.sh rough"
        ;;
        
    "demo")
        echo "🎬 Running workshop demo sequence..."
        echo "1. Running rough tests..."
        ./gradlew test --tests="*RoughTestsSpec" -Dgeb.headless=true --continue
        
        echo "2. Running organized tests..."  
        ./gradlew test --tests="*OrganizedTestsSpec" -Dgeb.headless=true --continue
        
        echo "3. Demonstrating parallel execution..."
        time ./gradlew parallelTest -Dgeb.headless=true
        
        echo "🎉 Demo complete!"
        ;;
        
    "help"|*)
        echo "Available commands:"
        echo "  rough      - Run rough/unorganized tests"
        echo "  organized  - Run organized tests with page objects"
        echo "  parallel   - Run parallel tests (timed)"
        echo "  tdd        - Run TDD examples"
        echo "  grid       - Run tests against Selenium Grid"
        echo "  all        - Run all tests"
        echo "  demo       - Run complete workshop demo"
        echo "  setup      - Setup workshop environment"
        echo "  clean      - Clean up environment"
        echo ""
        echo "Example: ./run-tests.sh rough"
        ;;
esac