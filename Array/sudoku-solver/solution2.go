package main

func solveSudoku(board [][]byte) {
	rowMask := make([]int, 9)
	colMask := make([]int, 9)
	boxMask := make([]int, 9)
	var empties [][2]int

	for i := 0; i < 9; i++ {
		for j := 0; j < 9; j++ {
			if board[i][j] != '.' {
				d := int(board[i][j] - '1')
				b := (i/3)*3 + (j/3)
				bit := 1 << d
				rowMask[i] |= bit
				colMask[j] |= bit
				boxMask[b] |= bit
			} else {
				empties = append(empties, [2]int{i, j})
			}
		}
	}

	var solved bool
	var backtrack func(idx int)
	backtrack = func(idx int) {
		if solved {
			return
		}
		if idx == len(empties) {
			solved = true
			return
		}

		// 选择最少可能空格
		best, min := idx, 10
		for k := idx; k < len(empties); k++ {
			i, j := empties[k][0], empties[k][1]
			b := (i/3)*3 + (j/3)
			used := rowMask[i] | colMask[j] | boxMask[b]
			cnt := 9 - bits.OnesCount(uint(used))
			if cnt < min {
				min = cnt
				best = k
				if cnt == 1 {
					break
				}
			}
		}
		empties[idx], empties[best] = empties[best], empties[idx]
		i, j := empties[idx][0], empties[idx][1]
		b := (i/3)*3 + (j/3)
		used := rowMask[i] | colMask[j] | boxMask[b]
		for bitsFree := (^used) & 0x1FF; bitsFree > 0; bitsFree &= bitsFree - 1 {
			bit := bitsFree & -bitsFree
			d := bits.TrailingZeros(uint(bit))
			board[i][j] = byte('1' + d)
			rowMask[i] |= bit
			colMask[j] |= bit
			boxMask[b] |= bit
			backtrack(idx + 1)
			if solved {
				return
			}
			board[i][j] = '.'
			rowMask[i] &= ^bit
			colMask[j] &= ^bit
			boxMask[b] &= ^bit
		}
		empties[idx], empties[best] = empties[best], empties[idx]
	}

	backtrack(0)
}
