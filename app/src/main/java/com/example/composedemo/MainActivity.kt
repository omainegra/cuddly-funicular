package com.example.composedemo

import android.app.UiAutomation
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.*
import com.example.composedemo.ui.theme.ComposeDemoTheme
import java.io.FileDescriptor
import java.io.PrintWriter

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val modifier = Modifier.semantics {
                testTag = ""
            }
            ComposeDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().semantics { testTagsAsResourceId = true },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text("Text 1")

                        Button(modifier = Modifier.semantics { testTag = "button1" }, onClick = {  }) {
                            Text("Button 1")
                        }

                        Text("Text 2")

                        Button(onClick = {  }) {
                            Text("Button 2")
                        }

                        IconButton(
                            modifier = Modifier
                                .semantics(mergeDescendants = true) { contentDescription = "Add" },
                            onClick = {  },
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        }

                        Row {
                            Text("Text 3")

                            Button(onClick = {  }) {
                                Text("Button 3")
                            }

                            Text("Text 4")

                            Button(onClick = {  }) {
                                Text("Button 4")
                            }
                        }
                    }
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            dump(findViewById<View>(android.R.id.content), PrintWriter(System.out))
        }, 1000L)
    }

    override fun dump(
        prefix: String,
        fd: FileDescriptor?,
        writer: PrintWriter,
        args: Array<out String>?
    ) {
        dump(findViewById<View>(android.R.id.content), writer)
    }

    private fun dump(view: View, writer: PrintWriter) {
        dump(view.createAccessibilityNodeInfo(), writer)
    }

    private fun dump(nodeInfo: AccessibilityNodeInfo, writer: PrintWriter) {
        try {
            dumpNode(nodeInfo, writer, "-")
        } catch (e: java.lang.Exception) {
            e.printStackTrace(writer)
        } finally {
            writer.flush()
        }
    }

    private fun dumpNode(nodeInfo: AccessibilityNodeInfo, writer: PrintWriter, prefix: String) {
        writer.println("$prefix text=${nodeInfo.text}")
        val n = nodeInfo.childCount

        writer.println("$prefix childCount=$n")

        for (i in 0 until n) {
            val child = nodeInfo.getChild(i)
            dumpNode(child, writer, "$prefix-")
        }
    }
}
