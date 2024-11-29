package com.example.game2048;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game2048.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Game2048Manager gameManager;
    private ActivityMainBinding binding;
    private GestureDetector gestureDetector;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "GamePrefs";
    private static final String HIGH_SCORE_KEY = "high_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        gameManager = new Game2048Manager();
        updateUI();

        gestureDetector = new GestureDetector(this, new SwipeGestureListener());
        GridLayout gridLayout = binding.gridLayout;
        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        // Restart button functionality
        binding.restartButton.setOnClickListener(v -> restartGame());

        // Display high score
       // displayHighScore();
    }

    private void performMove(String direction) {
        if (gameManager.move(direction)) {
            updateUI();
            if (isGameOver()) {
                showGameOver();
            }
        }
    }

    private void updateUI() {
        int[][] grid = gameManager.getGrid();
        // Update grid UI elements dynamically
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int tileValue = grid[i][j];
                updateTileUI(i, j, tileValue);
            }
        }

        // Update score
        binding.scoreValue.setText(String.valueOf(gameManager.getScore()));
    }

    private void updateTileUI(int row, int col, int value) {
        TextView tile = (TextView) binding.gridLayout.getChildAt(row * 4 + col);

        if (tile != null) {
            if (value == 0) {
                tile.setText("");
                tile.setBackgroundResource(R.drawable.tile_empty);
            } else {
                tile.setText(String.valueOf(value));
                tile.setBackgroundResource(getTileBackground(value));
            }
        }
    }

    private int getTileBackground(int value) {
        switch (value) {
            case 2: return R.drawable.tile_2;
            case 4: return R.drawable.tile_4;
            case 8: return R.drawable.tile_8;
            case 16: return R.drawable.tile_16;
            case 32: return R.drawable.tile_32;
            case 64: return R.drawable.tile_64;
            case 128: return R.drawable.tile_128;
            case 256: return R.drawable.tile_256;
            case 512: return R.drawable.tile_512;
            case 1024: return R.drawable.tile_1024;
            case 2048: return R.drawable.tile_2048;
            default: return R.drawable.tile_empty;
        }
    }

    private void restartGame() {
        gameManager = new Game2048Manager();
        updateUI();
        binding.gameOverText.setVisibility(View.GONE); // Hide "Game Over" when restarting
        binding.gridLayout.setClickable(true); // Re-enable grid for new game
    }

    private boolean isGameOver() {
        return !hasValidMoves();  // If no valid moves are left
    }

    private boolean hasValidMoves() {
        int[][] grid = gameManager.getGrid();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 0) return true; // Empty space
                if (i < 3 && grid[i][j] == grid[i + 1][j]) return true; // Merge down
                if (j < 3 && grid[i][j] == grid[i][j + 1]) return true; // Merge right
            }
        }
        return false; // No valid moves left
    }

    private void showGameOver() {
        // Show the "Game Over" text
        binding.gameOverText.setVisibility(View.VISIBLE);
        binding.gridLayout.setClickable(false); // Disable grid interaction

        // Save high score if necessary
        int currentScore = gameManager.getScore();
        int highScore = getHighScore();
        if (currentScore > highScore) {
            saveHighScore(currentScore);
        }
    }

    private void saveHighScore(int score) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HIGH_SCORE_KEY, score);
        editor.apply();
    }

    private int getHighScore() {
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0); // Default to 0 if no high score exists
    }

   // private void displayHighScore() {
   //    int highScore = getHighScore();
   //     binding.highScoreValue.setText(String.valueOf(highScore));
   // }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            try {
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            performMove("RIGHT");
                        } else {
                            performMove("LEFT");
                        }
                        return true;
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            performMove("DOWN");
                        } else {
                            performMove("UP");
                        }
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
