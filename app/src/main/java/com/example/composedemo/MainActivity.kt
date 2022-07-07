package com.example.composedemo

import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.composedemo.ui.theme.ComposeDemoTheme
import java.io.FileDescriptor
import java.io.PrintWriter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text("Text 1")

                        Button(onClick = {  }) {
                            Text("Button 1")
                        }

                        Text("Text 2")

                        Button(onClick = {  }) {
                            Text("Button 2")
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
    }

    override fun dump(
        prefix: String,
        fd: FileDescriptor?,
        writer: PrintWriter,
        args: Array<out String>?
    ) {
        val view: View = findViewById(android.R.id.content)
        val nodeInfo = view.createAccessibilityNodeInfo()

        try {
            dumpNode(nodeInfo, writer, "-")
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
