package com.example.tic_tac_toe_23009273

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val tableLayout = findViewById<TableLayout>(R.id.table_layout)
        tableLayout = findViewById<TableLayout>(R.id.table_layout)

        tableLayout.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )

        turnTextView = findViewById(R.id.turnTextView) as TextView
        //tableLayout = findViewById(R.id.table_layout) as TableLayout
        resetButton = findViewById(R.id.resetButton) as android.widget.Button
        //resetButton!!.setOnClickListener() {startNewGame( false )}
        resetButton!!.setOnClickListener() {
            resetScores = true //set the variable to true when the button is clicked
            startNewGame(false)
        }

        val settingsButton = findViewById(R.id.settingsButton) as android.widget.Button
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        startNewGame( true )
    }

    var gameBoard: Array<CharArray> = Array( 3 ) { CharArray( 3 ) }

    var turn = 'X'
    //var tableLayout: android.widget.TableLayout? = null
    var turnTextView: android.widget.TextView? = null
    var resetButton: android.widget.Button? = null

    //new added
    var settingsButton: android.widget.Button? = null
    var gridSize = 3
    var playerStart = (0..1).random()
    var xScore = 0
    var oScore = 0
    var resetScores = false //as we are calling one function, need to only reset score if the button is clicked!!
    var maxRounds = 0
    var currentRound = 0
    var aiEnabled = false

    //apply gridSize when user returns to the main activity from settings
    override fun onResume() {
        super.onResume()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /*or application*/)
        gridSize = sharedPreferences.getInt(SettingsActivity.GRID_SIZE, 3)
        //set rounds
        //val roundsString = sharedPreferences.getString("rounds", "1")
        //maxRounds = roundsString?.toIntOrNull() ?: 1
        aiEnabled = sharedPreferences.getBoolean("ai", false)
        startNewGame(true)
    }

    private fun startNewGame(setClickListener: Boolean) {
        //turn = 'X'
        if (playerStart == 0) {
            'X'
        } else {
            'O'
        }

        if (resetScores) {
            xScore = 0
            oScore = 0
            resetScores = false //set back to false as scores rset after player clicks reset button
        }
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
        gameBoard = Array(gridSize) { CharArray(gridSize) }
        tableLayout.removeAllViews()
        val xScoreTextView = findViewById<TextView>(R.id.xplayerscore)
        xScoreTextView.text = String.format(getString(R.string.x_score), xScore)

        val oScoreTextView = findViewById<TextView>(R.id.oplayerscore)
        oScoreTextView.text = String.format(getString(R.string.o_score), oScore)

        val roundsTextView = findViewById<TextView>(R.id.rounds)
        val roundsString = resources.getString(R.string.rounds)
        roundsTextView.text = String.format(roundsString, maxRounds)

        for (i in 0 until gridSize) {
            val row = TableRow(ContextThemeWrapper(this, R.style.Cell))
            val layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            row.layoutParams = layoutParams
            tableLayout.addView(row)

            for (j in 0 until gridSize) {
                val cell = TextView(this)
                val cellLayoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )
                cellLayoutParams.setMargins(10, 10, 10, 0)
                //cellLayoutParams.height = 200 // Set a fixed height for the TextView
                //aduster height of the cell based on the grid size
                if (gridSize == 3) {
                    cellLayoutParams.height = 350
                }
                else if (gridSize == 4) {
                    cellLayoutParams.height = 250
                }
                else if (gridSize ==5) {
                    cellLayoutParams.height = 200
                }
                cellLayoutParams.width = 100 // Set a fixed width for the TextView
                cell.layoutParams = cellLayoutParams
                cell.gravity = Gravity.CENTER
                //cell.setBackgroundColor(Color.LTGRAY)
                cell.background = ContextCompat.getDrawable(this, R.drawable.rounded_corners)
                cell.setTextAppearance(R.style.Cell)
                gameBoard[i][j] = ' '
                cell.setOnClickListener { cellClickListener(i, j) }
                row.addView(cell)
            }
        }
    }



    //private fun startNewGame(setClickListener: Boolean) {
    //    turn = 'X'
    //    turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
    //    gameBoard = Array(3) { CharArray(3) }
    //    for (i in 0 until 3) {
    //        for (j in 0 until 3) {
    //            gameBoard[i][j] = ' '
    //            val cell = (tableLayout?.getChildAt(i) as android.widget.TableRow).getChildAt(j) as android.widget.TextView
    //            cell.text = ""
    //            if (setClickListener) {
    //                cell.setOnClickListener { cellClickListener(i, j) }
    //            }
    //        }
    //    }
    //}

    //private fun cellClickListener(row: Int, column: Int) {
    //    if (gameBoard[row][column] == ' ') {
    //        gameBoard[row][column] = turn

    //        ((tableLayout?.getChildAt(row) as android.widget.TableRow).getChildAt(column) as TextView).text = turn.toString()
    //        turn = if ('X' == turn) 'O' else 'X'
    //        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
    //        checkGameStatus()
    //    }
    //}

    private fun cellClickListener(row: Int, column: Int) {
        if (gameBoard[row][column] == ' ') {
            gameBoard[row][column] = turn

            if (::tableLayout.isInitialized) {
                val tableRow = tableLayout.getChildAt(row) as? TableRow
                val view = tableRow?.getChildAt(column)

                if (view is TextView) {
                    view.text = turn.toString()
                    turn = if ('X' == turn) 'O' else 'X'
                    turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
                    checkGameStatus()
                    if (aiEnabled == true) {
                        aiMove() //make ai move after player
                    }
                }
            }
        }
    }

    private fun isBoardFull (gameBoard:Array<CharArray>): Boolean {
        for (i in 0 until gameBoard.size) {
            for (j in 0 until gameBoard[i].size) {
                if (gameBoard[i][j] == ' ') {
                    return false
                }
            }
        }
        return true
    }

    //private fun isWinner (gameBoard:Array<CharArray>, w: Char): Boolean {
    //    for (i in 0 until gameBoard.size) {
    //        if (gameBoard[i][0] == w && gameBoard[i][1] == w && gameBoard[i][2] == w) {
    //            return true
    //        }
    //        if (gameBoard[0][i] == w && gameBoard[1][i] == w && gameBoard[2][i] == w) {
    //            return true
    //        }
    //    }
    //    if ((gameBoard[0][0] == w && gameBoard[1][1] == w && gameBoard[2][2] == w) ||
    //        (gameBoard[0][2] == w && gameBoard[1][1] == w && gameBoard[2][0] == w)) {
    //            return true
    //    }
    //    return false
    //}

    private fun isWinner (gameBoard:Array<CharArray>, w: Char): Boolean {
        //if gameboard.size is equals to 3, check winning conditions on a 3x3 board
        if (gameBoard.size == 3) {
            for (i in 0 until gameBoard.size) {
                if (gameBoard[i][0] == w && gameBoard[i][1] == w && gameBoard[i][2] == w) {
                    return true
                }
                if (gameBoard[0][i] == w && gameBoard[1][i] == w && gameBoard[2][i] == w) {
                    return true
                }
            }
            if ((gameBoard[0][0] == w && gameBoard[1][1] == w && gameBoard[2][2] == w) ||
                (gameBoard[0][2] == w && gameBoard[1][1] == w && gameBoard[2][0] == w)) {
                    return true
            }
        }
        //if gameboard size is equals to 4, check winning conditions on a 4x4 board
        else if (gameBoard.size == 4) {
            for (i in 0 until gameBoard.size) {
                if (gameBoard[i][0] == w && gameBoard[i][1] == w && gameBoard[i][2] == w && gameBoard[i][3] == w) {
                    return true
                }
                if (gameBoard[0][i] == w && gameBoard[1][i] == w && gameBoard[2][i] == w && gameBoard[3][i] == w) {
                    return true
                }
            }
            if ((gameBoard[0][0] == w && gameBoard[1][1] == w && gameBoard[2][2] == w && gameBoard[3][3] == w) ||
                (gameBoard[0][3] == w && gameBoard[1][2] == w && gameBoard[2][1] == w && gameBoard[3][0] == w)) {
                    return true
            }
        }
        //if gameboard size is equals to 5, check winning conditions on a 5x5 board
        else if (gameBoard.size == 5) {
            for (i in 0 until gameBoard.size) {
                if (gameBoard[i][0] == w && gameBoard[i][1] == w && gameBoard[i][2] == w && gameBoard[i][3] == w && gameBoard[i][4] == w) {
                    return true
                }
                if (gameBoard[0][i] == w && gameBoard[1][i] == w && gameBoard[2][i] == w && gameBoard[3][i] == w && gameBoard[4][i] == w) {
                    return true
                }
            }
            if ((gameBoard[0][0] == w && gameBoard[1][1] == w && gameBoard[2][2] == w && gameBoard[3][3] == w && gameBoard[4][4] == w) ||
                (gameBoard[0][4] == w && gameBoard[1][3] == w && gameBoard[2][2] == w && gameBoard[3][1] == w && gameBoard[4][0] == w)) {
                    return true
            }
        }
        return false
    }

    private fun checkGameStatus() {
        var state: String? = null
        if (isWinner(gameBoard, 'X')) {
            state = String.format(resources.getString(R.string.winner), 'X')
            //if x wins add 1
            xScore++
            currentRound++
        } else if (isWinner(gameBoard, 'O')) {
            state = String.format(resources.getString(R.string.winner), 'O')
            //if o wins add 1
            oScore++
            currentRound++
        } else {
            if (isBoardFull(gameBoard)) {
                state = resources.getString(R.string.draw)
            }
        }

        //update the text in the TextViews to display the updated scores
        val xScoreTextView = findViewById<TextView>(R.id.xplayerscore)
        xScoreTextView.text = String.format(getString(R.string.x_score), xScore)

        val oScoreTextView = findViewById<TextView>(R.id.oplayerscore)
        oScoreTextView.text = String.format(getString(R.string.o_score), oScore)

        if (state != null) {
            turnTextView?.text = state
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setMessage(state)
            builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                startNewGame(false)
            }
            var dialog = builder.create()
            dialog.show()
        }
    }

    //new added functions below

    private fun aiMove() {
        var row: Int
        var col: Int

        do {
            row = (0..2).random()
            col = (0..2).random()
        } while (gameBoard[row][col] != ' ')

        gameBoard[row][col] = turn

        if (::tableLayout.isInitialized) {
            val tableRow = tableLayout.getChildAt(row) as? TableRow
            val view = tableRow?.getChildAt(col)

            if (view is TextView) {
                view.text = turn.toString()
                turn = if ('X' == turn) 'O' else 'X'
                turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
                //checkGameStatus()
            }
        }
    }
}