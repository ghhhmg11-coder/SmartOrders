package com.smartorders

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.smartorders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val REQUEST_OVERLAY_PERMISSION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToggleOverlay.setOnClickListener {
            toggleOverlayService()
        }

        binding.btnEnableAccessibility.setOnClickListener {
            openAccessibilitySettings()
        }

        binding.btnLogout.setOnClickListener {
            confirmLogout()
        }

        updateOverlayButtonState()
    }

    override fun onResume() {
        super.onResume()
        updateOverlayButtonState()
    }

    private fun toggleOverlayService() {
        if (!Settings.canDrawOverlays(this)) {
            requestOverlayPermission()
            return
        }

        val prefs = getSharedPreferences("overlay_prefs", MODE_PRIVATE)
        val isRunning = prefs.getBoolean("overlay_running", false)

        if (isRunning) {
            stopService(Intent(this, OverlayService::class.java))
            prefs.edit().putBoolean("overlay_running", false).apply()
            binding.btnToggleOverlay.text = getString(R.string.btn_start_overlay)
            Toast.makeText(this, getString(R.string.overlay_stopped), Toast.LENGTH_SHORT).show()
        } else {
            startForegroundService(Intent(this, OverlayService::class.java))
            prefs.edit().putBoolean("overlay_running", true).apply()
            binding.btnToggleOverlay.text = getString(R.string.btn_stop_overlay)
            Toast.makeText(this, getString(R.string.overlay_started), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateOverlayButtonState() {
        val prefs = getSharedPreferences("overlay_prefs", MODE_PRIVATE)
        val isRunning = prefs.getBoolean("overlay_running", false)
        binding.btnToggleOverlay.text = if (isRunning)
            getString(R.string.btn_stop_overlay)
        else
            getString(R.string.btn_start_overlay)
    }

    private fun requestOverlayPermission() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required_title))
            .setMessage(getString(R.string.permission_required_message))
            .setPositiveButton(getString(R.string.btn_grant)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
            }
            .setNegativeButton(getString(R.string.btn_cancel), null)
            .show()
    }

    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
        Toast.makeText(this, getString(R.string.accessibility_hint), Toast.LENGTH_LONG).show()
    }

    private fun confirmLogout() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logout_title))
            .setMessage(getString(R.string.logout_message))
            .setPositiveButton(getString(R.string.btn_confirm)) { _, _ ->
                stopService(Intent(this, OverlayService::class.java))
                getSharedPreferences("overlay_prefs", MODE_PRIVATE)
                    .edit().putBoolean("overlay_running", false).apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton(getString(R.string.btn_cancel), null)
            .show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
