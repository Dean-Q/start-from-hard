class Solution:
    def solveSudoku(self, board: list[list[str]]) -> None:
        row_used = [[False] * 9 for _ in range(9)]
        col_used = [[False] * 9 for _ in range(9)]
        box_used = [[False] * 9 for _ in range(9)]
        self.board = board
        self.solved = False

        for i in range(9):
            for j in range(9):
                c = board[i][j]
                if c != '.':
                    d = int(c) - 1
                    b = (i // 3) * 3 + (j // 3)
                    row_used[i][d] = col_used[j][d] = box_used[b][d] = True

        def backtrack(i, j):
            if i == 9:
                self.solved = True
                return
            if j == 9:
                backtrack(i + 1, 0)
                return
            if board[i][j] != '.':
                backtrack(i, j + 1)
                return
            b = (i // 3) * 3 + (j // 3)
            for d in range(9):
                if not row_used[i][d] and not col_used[j][d] and not box_used[b][d]:
                    board[i][j] = str(d + 1)
                    row_used[i][d] = col_used[j][d] = box_used[b][d] = True
                    backtrack(i, j + 1)
                    if self.solved:
                        return
                    board[i][j] = '.'
                    row_used[i][d] = col_used[j][d] = box_used[b][d] = False

        backtrack(0, 0)
