package main

func solveSudoku(board [][]byte) {
	rowUsed := make([][]bool, 9)
	colUsed := make([][]bool, 9)
	boxUsed := make([][]bool, 9)
	for i := 0; i < 9; i++ {
		rowUsed[i] = make([]bool, 9)
		colUsed[i] = make([]bool, 9)
		boxUsed[i] = make([]bool, 9)
	}

	for i := 0; i < 9; i++ {
		for j := 0; j < 9; j++ {
			if board[i][j] != '.' {
				d := board[i][j] - '1'
				b := (i/3)*3 + (j/3)
				rowUsed[i][d] = true
				colUsed[j][d] = true
				boxUsed[b][d] = true
			}
		}
	}

	var solved bool

	var backtrack func(i, j int)
	backtrack = func(i, j int) {
		if i == 9 {
			solved = true
			return
		}
		if j == 9 {
			backtrack(i+1, 0)
			return
		}
		if board[i][j] != '.' {
			backtrack(i, j+1)
			return
		}

		b := (i/3)*3 + (j/3)
		for d := byte(0); d < 9; d++ {
			if !rowUsed[i][d] && !colUsed[j][d] && !boxUsed[b][d] {
				board[i][j] = '1' + d
				rowUsed[i][d], colUsed[j][d], boxUsed[b][d] = true, true, true

				backtrack(i, j+1)
				if solved {
					return
				}

				board[i][j] = '.'
				rowUsed[i][d], colUsed[j][d], boxUsed[b][d] = false, false, false
			}
		}
	}

	backtrack(0, 0)
}
