package com.example.composedemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import com.example.composedemo.ui.theme.ComposeDemoTheme
import java.io.FileDescriptor
import java.io.PrintWriter

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var checkbox1Checked by remember { mutableStateOf(false) }
            var textFieldValue by remember { mutableStateOf("") }

            ComposeDemoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { testTagsAsResourceId = true },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Text 1")

                        Button(modifier = Modifier.semantics { testTag = "button1" }, onClick = {  }) {
                            Text("Button 1")
                        }

                        Text("Text 2")

                        Button(onClick = {  }) {
                            Text("Button 2")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            var count by remember { mutableStateOf(0) }

                            IconButton(
                                modifier = Modifier
                                    .semantics(mergeDescendants = true) { contentDescription = "Add" },
                                onClick = { count += 1 },
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = null)
                            }

                            Text(modifier = Modifier.semantics { testTag = "counterText" }, text = "$count")

                            IconButton(
                                modifier = Modifier
                                    .semantics(mergeDescendants = true) { contentDescription = "Minus" },
                                onClick = { count -= 1 },
                            ) {
                                Icon(Icons.Filled.Remove, contentDescription = null)
                            }
                        }


                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = checkbox1Checked,
                                onCheckedChange = { checkbox1Checked = !checkbox1Checked },
                            )
                            Text("CheckBox 1")
                        }

                        BasicTextField(value = textFieldValue, onValueChange = { textFieldValue = it })

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text("Text 3")

                            Button(onClick = {  }) {
                                Text("Button 3")
                            }

                            Text("Text 4")

                            Button(onClick = {  }) {
                                Text("Button 4")
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .size(300.dp)
                                .semantics { contentDescription = "Items"},
                        ) {
                            items((1..1000).toList()) { item ->
                                Text("Item: $item")
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
