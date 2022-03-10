#!/usr/bin/env groovy
package org.bala

class GlobalVars {
   static String foo = "bar"
   // refer to this in a pipeline using:
   // import com.cleverbuilder.GlobalVars
   // println GlobalVars.foo
}
