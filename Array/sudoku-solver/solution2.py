class Solution:
    def solveSudoku(self, board: list[list[str]]) -> None:
        row_mask = [0] * 9
        col_mask = [0] * 9
        box_mask = [0] * 9
        empties = []

        for i in range(9):
            for j in range(9):
                if board[i][j] != '.':
                    d = int(board[i][j]) - 1
                    bit = 1 << d
                    b = (i // 3) * 3 + (j // 3)
                    row_mask[i] |= bit
                    col_mask[j] |= bit
                    box_mask[b] |= bit
                else:
                    empties.append((i, j))

        def backtrack(idx=0):
            nonlocal row_mask, col_mask, box_mask
            if idx == len(empties):
                return True

            # 选择最少可能空格
            best, min_cnt = idx, 10
            for k in range(idx, len(empties)):
                i, j = empties[k]
                b = (i // 3) * 3 + (j // 3)
                used = row_mask[i] | col_mask[j] | box_mask[b]
                cnt = 9 - bin(used).count('1')
                if cnt < min_cnt:
                    min_cnt = cnt
                    best = k
                    if cnt == 1: break
            empties[idx], empties[best] = empties[best], empties[idx]
            i, j = empties[idx]
            b = (i // 3) * 3 + (j // 3)
            used = row_mask[i] | col_mask[j] | box_mask[b]
            bits = (~used) & 0x1FF

            while bits:
                bit = bits & -bits
                bits &= bits - 1
                d = (bit.bit_length() - 1)
                board[i][j] = str(d + 1)
                row_mask[i] |= bit
                col_mask[j] |= bit
                box_mask[b] |= bit
                if backtrack(idx + 1):
                    return True
                board[i][j] = '.'
                row_mask[i] &= ~bit
                col_mask[j] &= ~bit
                box_mask[b] &= ~bit
            empties[idx], empties[best] = empties[best], empties[idx]
            return False

        backtrack()
