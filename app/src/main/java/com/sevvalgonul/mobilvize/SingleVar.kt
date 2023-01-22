package com.sevvalgonul.mobilvize

class SingleVar {
    companion object {
        private var firstRun: Boolean = false

        fun isFirstRun(): Boolean {
            return firstRun
        }

        fun setFirstRun(run: Boolean) {
            firstRun = run;
        }
    }

}
