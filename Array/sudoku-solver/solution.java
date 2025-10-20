class Solution {
    private char[][] board;
    private List<int[]> empties = new ArrayList<>();
    private boolean solved = false;

    // 使用位掩码记录使用状态
    private int[] rowMask = new int[9];
    private int[] colMask = new int[9];
    private int[] boxMask = new int[9];

    public void solveSudoku(char[][] board) {
        this.board = board;
        // 初始化 masks 和 empties
        for (int i = 0; i < 9; i++) {
            rowMask[i] = 0;
            colMask[i] = 0;
            boxMask[i] = 0;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') {
                    empties.add(new int[]{i, j});
                } else {
                    int d = c - '1';  // 0 ~ 8
                    int bit = 1 << d;
                    rowMask[i] |= bit;
                    colMask[j] |= bit;
                    int b = (i / 3) * 3 + (j / 3);
                    boxMask[b] |= bit;
                }
            }
        }
        backtrack(0);
    }

    private void backtrack(int idx) {
        if (idx == empties.size()) {
            solved = true;
            return;
        }
        // 可以选择启发式：找当前最难填的空格（可选数字最少的那个）
        int best = -1;
        int minOptions = 10;  // 最少的可能性数
        for (int k = idx; k < empties.size(); k++) {
            int i = empties.get(k)[0], j = empties.get(k)[1];
            int b = (i / 3) * 3 + (j / 3);
            int used = rowMask[i] | colMask[j] | boxMask[b];
            int options = 9 - Integer.bitCount(used);
            if (options < minOptions) {
                minOptions = options;
                best = k;
                if (options == 1) break;  // 最极端就一个选择，不能更差
            }
        }
        // 把 best 交换到当前位置 idx
        Collections.swap(empties, idx, best);
        int i = empties.get(idx)[0], j = empties.get(idx)[1];
        int b = (i / 3) * 3 + (j / 3);
        int used = rowMask[i] | colMask[j] | boxMask[b];
        // 找哪些数字可填（1 ~ 9 中掩码为 0 的位）
        for (int d = 0; d < 9; d++) {
            int bit = 1 << d;
            if ((used & bit) == 0) {
                // 可以填
                board[i][j] = (char) (d + '1');
                rowMask[i] |= bit;
                colMask[j] |= bit;
                boxMask[b] |= bit;

                backtrack(idx + 1);
                if (solved) return;

                // 回溯
                board[i][j] = '.';
                rowMask[i] &= ~bit;
                colMask[j] &= ~bit;
                boxMask[b] &= ~bit;
            }
        }
        // 恢复顺序（可选）
        Collections.swap(empties, idx, best);
    }
}
