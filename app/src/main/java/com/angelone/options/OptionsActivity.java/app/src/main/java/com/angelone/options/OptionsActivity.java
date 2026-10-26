package com.angelone.options;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main Activity for Angel One SmartAPI Options Trading App.
 * 
 * Features:
 * - NIFTY 50 and BSE SENSEX options analysis
 * - 5-minute chart display (placeholder)
 * - Paper trading / backtest interface
 * - ₹10,000 risk management settings
 * - Live trading DISABLED by default
 * 
 * NOTE: This is a minimal skeleton. API integration with Angel One SmartAPI
 * requires proper authentication keys from the broker. This app ships with
 * live trading disabled and paper-trading as the default mode.
 */
public class OptionsActivity extends AppCompatActivity {

    // Trading mode: paper = simulated, live = real money
    // DEFAULT: paper trading ONLY. Live trading must be explicitly enabled
    // by the user through settings with a separate confirmation flow.
    private static final String TRADING_MODE_PAPER = "paper";
    private static final String TRADING_MODE_LIVE = "live";
    private String tradingMode = TRADING_MODE_PAPER; // safe default

    // Risk management constants
    private static final double MAX_RISK_PER_TRADE = 10000.0; // ₹10,000

    // Indices supported
    private static final String INDEX_NIFTY = "NIFTY 50";
    private static final String INDEX_SENSEX = "BSE SENSEX";

    // Candle interval
    private static final String INTERVAL_5MIN = "5"; // 5-minute candles

    private TextView tvIndex, tvStrategy, tvRisk, tvStatus;
    private Button btnRunBacktest, btnEnableLive, btnPaperMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        tvIndex = findViewById(R.id.tvIndex);
        tvStrategy = findViewById(R.id.tvStrategy);
        tvRisk = findViewById(R.id.tvRisk);
        tvStatus = findViewById(R.id.tvStatus);
        btnRunBacktest = findViewById(R.id.btnRunBacktest);
        btnEnableLive = findViewById(R.id.btnEnableLive);
        btnPaperMode = findViewById(R.id.btnPaperMode);

        // Set initial display
        tvIndex.setText("Selected Index: " + INDEX_NIFTY);
        tvStrategy.setText("Strategy: Paper Backtest");
        tvRisk.setText("Max Risk Per Trade: ₹" + (int) MAX_RISK_PER_TRADE);
        tvStatus.setText("Live Trading: " + getStatusText());

        // Backtest button - runs paper trading simulation
        btnRunBacktest.setOnClickListener(v -> {
            runPaperBacktest();
        });

        // Enable live trading button
        btnEnableLive.setOnClickListener(v -> {
            showLiveTradingDisabledMessage();
            // TODO: Add explicit confirmation dialog before allowing live mode
        });

        // Switch back to paper mode
        btnPaperMode.setOnClickListener(v -> {
            setTradingMode(TRADING_MODE_PAPER);
            updateStatus();
        });
    }

    /**
     * Simulates a paper backtest run for the selected index and strategy.
     * In a real implementation this would connect to historical data sources
     * via the SmartAPI (or a local CSV/database of OHLCV data).
     */
    private void runPaperBacktest() {
        tvStatus.setText("Running paper backtest...");
        tvStatus.postDelayed(() -> {
            // Placeholder: In production, this would execute strategy logic
            Toast.makeText(this,
                "Paper backtest complete for " + INDEX_NIFTY
                + " on " + INTERVAL_5MIN + "-min chart.",
                Toast.LENGTH_LONG).show();
            tvStatus.setText("Paper backtest complete. Live Trading: "
                + getStatusText());
        }, 1000);
    }

    /**
     * Shows a warning that live trading is disabled by default.
     * Broker API keys must never be hardcoded.
     */
    private void showLiveTradingDisabledMessage() {
        Toast.makeText(this,
            "Live trading is DISABLED. API keys must be configured securely\n"
            + "in app/src/main/assets/smartapi_config.properties\n"
            + "(not committed to repository).",
            Toast.LENGTH_LONG).show();
    }

    private void setTradingMode(String mode) {
        this.tradingMode = mode;
        updateStatus();
    }

    private String getStatusText() {
        return tradingMode.equals(TRADING_MODE_LIVE) ? "ENABLED" : "DISABLED";
    }

    private void updateStatus() {
        tvStatus.setText("Live Trading: " + getStatusText());
        if (tradingMode.equals(TRADING_MODE_LIVE)) {
            Toast.makeText(this,
                "WARNING: Live trading is now ACTIVE. "
                + "Orders will go to the market!",
                Toast.LENGTH_LONG).show();
        }
    }
}
