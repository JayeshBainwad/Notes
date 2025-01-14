package com.jsb.notes.feature_note.presentation.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jsb.notes.ui.theme.LightGray

@Composable
fun DrawerScreen(
    modifier: Modifier = Modifier,
    items: List<MenuItem>,
    onClick: (MenuItem) -> Unit,
    textStyle: TextStyle = TextStyle(fontSize = 30.sp, color = MaterialTheme.colorScheme.onSurface)
) {
    LazyColumn(
        modifier = modifier
            .width(320.dp)
            .fillMaxHeight()
    ) {
        items(items){menuItem ->
            Box(
                modifier = modifier
                    .padding(top = 16.dp, start = 6.dp, end = 6.dp)
                    .clickable {
                        onClick(menuItem)
                    }
            ) {
                Row(
                    modifier = modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = if (menuItem.isSelected) LightGray else Color.Transparent)
                        .padding(start = 4.dp)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    menuItem.icon?.let { Icon(
                        imageVector = it,
                        contentDescription = menuItem.description,
                        tint = MaterialTheme.colorScheme.onSurface
                    ) }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = menuItem.title,
                        style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}