package com.marsn.minitalk.ui.feature.addedit

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import androidx.room.util.getColumnIndex
import com.marsn.minitalk.data.todo.TodoDatabaseProvider
import com.marsn.minitalk.data.todo.TodoRepositoryImpl
import com.marsn.minitalk.ui.UIEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddEditToDoScreen(
    id: Long?,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provider(context)
    val repository = TodoRepositoryImpl(
        todoDao = database.todoDao
    )
    val viewModel = viewModel<AddEditViewModel> {
        AddEditViewModel(
            id = id,
            todoRepository = repository
        )
    }

    val title = viewModel.title
    val description = viewModel.description
    val snackbarHostState = remember() {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is UIEvent.NavigateBack -> {
                    navigateBack()
                }

                is UIEvent.NavigateTo<*> -> {}
            }
        }

    }

    AddEditContent(
    )
}


@Composable
fun AddEditContent(
) {
    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .background(Color(0xFFE3F2FD))
                .padding(16.dp)
        ) {

            BottomSheetProva()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetProva() {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp,
        sheetDragHandle = { Drag() },
        sheetContent = {
            InboxSheetContainer(coroutineScope, sheetState)
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFE3F2FD)),
        ) {
            Column {
                Text("👤 Aluno: João Silva", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(8.dp))
                 Row(
                    modifier = Modifier
                        .fillMaxWidth() // 👈 antes era 40.dp
                        .height(1.dp) // 👈 pode engrossar um pouco também
                        .background(Color.LightGray, RoundedCornerShape(3.dp)),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                }
                Spacer(Modifier.height(8.dp))
                Text("🧑‍🏫 Examinador: Carlos Pereira", fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                 Row(
                    modifier = Modifier
                        .fillMaxWidth() // 👈 antes era 40.dp
                        .height(1.dp) // 👈 pode engrossar um pouco também
                        .background(Color.LightGray, RoundedCornerShape(3.dp)),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                }
                Spacer(Modifier.height(8.dp))
                Text("🚗 Placa: ABC-1234", fontSize = 18.sp)
                Spacer(Modifier.height(16.dp))
                TabelaDeAvaliacao()
            }
        }
    }
}


@Composable
fun TabelaDeAvaliacao() {
    val avaliacoes = listOf(
        AvaliacaoItem("🚗", "Partida ou saída", false),
        AvaliacaoItem("🪞", "Uso dos retrovisores", false),
        AvaliacaoItem("🚦", "Sinalização ou observação", true),
        AvaliacaoItem("🅿️", "Estacionamento", false),
        AvaliacaoItem("⚙️", "Engrenagens", true),
        AvaliacaoItem("⛔", "Parada", false)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(8.dp)
    ) {
        itemsIndexed(avaliacoes) { index, item ->


            LinhaAvaliacao(item)
            if (index < avaliacoes.lastIndex) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // 👈 antes era 40.dp
                        .height(1.dp) // 👈 pode engrossar um pouco também
                        .background(Color.LightGray, RoundedCornerShape(3.dp)),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                }
            }

        }
    }
}

data class AvaliacaoItem(
    val icone: String,
    val titulo: String,
    val ok: Boolean
)

@Composable
fun LinhaAvaliacao(item: AvaliacaoItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.icone,
                fontSize = 20.sp
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = item.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // 🔹 Botão de status (FALTA ou OK)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (item.ok) Color(0xFF4CAF50) else Color(0xFFF44336))
                .padding(horizontal = 16.dp, vertical = 4.dp).width(32.dp)
        ) {
            Text(
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Center),
                text = if (item.ok) "OK" else "Falta",
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxSheetContainer(coroutineScope: CoroutineScope, sheetState: SheetState) {


    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Spacer(Modifier.height(12.dp))
        Text("👤 Aluno: João Silva", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text("🧑‍🏫 Examinador: Carlos Pereira", fontSize = 16.sp)
        Text("🚗 Placa: ABC-1234", fontSize = 16.sp)
        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Button(
                    onClick = {
                        coroutineScope.launch { sheetState.show() } // 👈 Expande total
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("FINALIZAR PROVA", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch { sheetState.partialExpand() } // 👈 Recolhe
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    border = BorderStroke(1.dp, Color(0xFFF44336)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                ) {
                    Text("CANCELAR PROVA", color = Color.White)
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}


@Composable
fun Drag() {
    Row(
        modifier = Modifier
            .width(80.dp) // 👈 antes era 40.dp
            .height(5.dp) // 👈 pode engrossar um pouco também
            .background(Color.LightGray, RoundedCornerShape(3.dp)),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
    }
}

@Preview
@Composable
fun AddEditToDoScreenPreview() {
    AddEditToDoScreen(null, navigateBack = {})
}

@Preview
@Composable
fun UberStyleBottomSheetScaffoldPreview() {
    BottomSheetProva()
}

