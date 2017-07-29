[travis-ci.org](http://www.travis-ci.org)<br/>
[![Build Status](https://travis-ci.org/peterarsentev/code_quality_principles.svg?branch=master)](https://travis-ci.org/peterarsentev/code_quality_principles)



Code quality principles.
========================

The project contains principles, which improve code quality.

Below, you can find list of principles. 
Each principles has examples of bad and good code snippets with explanations.

Contribute
----------

I will appreciate, if you share challenges code snippets or add other useful principles with examples.
If you have any questions, feel free to contact me. Skype : petrarsentev

List of principles.
-------------------

1. Multiple return statements.

All methods must have only an one return statement. It should be at the end of method.

Bad code.

    int max(int left, int right) {
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }
    
Good code.

    int max(int left, int right) {
        return left > right ? left : right;
    }