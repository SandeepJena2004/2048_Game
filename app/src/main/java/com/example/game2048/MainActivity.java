package com.example.game2048;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game2048.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Game2048Manager gameManager; // Updated class name
    private ActivityMainBinding binding;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gameManager = new Game2048Manager(); // Initialize the game manager
        updateUI();

        // Setup gesture detector for swipe gestures
        gestureDetector = new GestureDetector(this, new SwipeGestureDetector());
        binding.getRoot().setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void handleMove(String direction) {
        if (gameManager.move(direction)) {
            updateUI(); // Update UI after a successful move
        }
    }

    private void updateUI() {
        int[][] grid = gameManager.getGrid();

        // Update grid UI elements dynamically
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int tileValue = grid[i][j];
                // Update the UI for each tile based on its value
                updateTileUI(i, j, tileValue);
            }
        }
    }

    private void updateTileUI(int row, int col, int value) {
        // Calculate the index of the child in the grid layout (row-major order)
        View tile = binding.gridLayout.getChildAt(row * 4 + col);

        if (tile instanceof TextView) {
            TextView tileView = (TextView) tile;
            if (value == 0) {
                tileView.setText("");
                tileView.setBackgroundResource(R.drawable.tile_empty);
            } else {
                tileView.setText(String.valueOf(value));
                tileView.setBackgroundResource(getTileBackground(value));
            }
        }
    }

    private int getTileBackground(int value) {
        // Return appropriate drawable resource for each tile value
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
            default: return R.drawable.tile_empty; // Default background for unknown values
        }
    }

    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        handleMove("RIGHT"); // Right swipe
                    } else {
                        handleMove("LEFT"); // Left swipe
                    }
                    return true;
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        handleMove("DOWN"); // Down swipe
                    } else {
                        handleMove("UP"); // Up swipe
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
