class Solution {
    // 标记：rowUsed[i][d] 表示行 i 上数字 d 是否已被使用
    private boolean[][] rowUsed = new boolean[9][9];
    // colUsed[j][d]：列 j 上数字 d 是否已被使用
    private boolean[][] colUsed = new boolean[9][9];
    // boxUsed[b][d]：第 b 个 3×3 宫格上数字 d 是否被使用
    // b 从 0 到 8，按 (i/3, j/3) 映射
    private boolean[][] boxUsed = new boolean[9][9];
    
    private char[][] board;
    private boolean solved = false;

    public void solveSudoku(char[][] board) {
        this.board = board;
        // 初始化状态
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c != '.') {
                    int d = c - '1';  // 转成 0~8
                    rowUsed[i][d] = true;
                    colUsed[j][d] = true;
                    int b = (i / 3) * 3 + (j / 3);
                    boxUsed[b][d] = true;
                }
            }
        }
        // 开始回溯
        backtrack(0, 0);
    }

    // 从位置 (i, j) 开始填
    private void backtrack(int i, int j) {
        // 如果 j == 9，就换下一行，从列 0 开始
        if (j == 9) {
            backtrack(i + 1, 0);
            return;
        }
        // 如果行越界，说明填完了
        if (i == 9) {
            solved = true;
            return;
        }
        // 如果当前位置已经有数字，跳过
        if (board[i][j] != '.') {
            backtrack(i, j + 1);
            if (solved) return;
        } else {
            int b = (i / 3) * 3 + (j / 3);
            for (int d = 0; d < 9; d++) {
                if (!rowUsed[i][d] && !colUsed[j][d] && !boxUsed[b][d]) {
                    // 尝试填 d
                    board[i][j] = (char) (d + '1');
                    rowUsed[i][d] = colUsed[j][d] = boxUsed[b][d] = true;

                    backtrack(i, j + 1);
                    if (solved) return;

                    // 回溯
                    board[i][j] = '.';
                    rowUsed[i][d] = colUsed[j][d] = boxUsed[b][d] = false;
                }
            }
        }
    }
}
