package com.kozlovskiy.avitoweather.domain.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kozlovskiy.avitoweather.R

object DialogUtils {
    fun getUnknownErrorDialog(
        context: Context,
        onRetryAction: () -> Unit,
        onDismissAction: () -> Unit,
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(R.string.unknown_error)
            .setMessage(R.string.unknown_error_message)
            .setPositiveButton(R.string.retry) { di, _ ->
                onRetryAction()
                di.cancel()
            }
            .setNegativeButton(R.string.cancel) { di, _ ->
                onDismissAction()
                di.cancel()
            }
            .setOnDismissListener {
                it.cancel()
                onDismissAction()
            }
            .create()
    }

    fun getBadLocationDialog(
        context: Context,
        onRetryAction: () -> Unit,
        onManuallySelectAction: () -> Unit,
        onDismissAction: () -> Unit,
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(R.string.bad_location)
            .setMessage(R.string.bad_location_message)
            .setPositiveButton(R.string.retry) { di, _ ->
                onRetryAction()
                di.cancel()
            }
            .setNegativeButton(R.string.cancel) { di, _ ->
                onManuallySelectAction()
                di.cancel()
            }
            .setOnDismissListener {
                it.cancel()
                onDismissAction()
            }
            .create()
    }
}