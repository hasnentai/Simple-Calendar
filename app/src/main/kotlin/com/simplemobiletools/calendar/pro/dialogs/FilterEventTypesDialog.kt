package com.simplemobiletools.calendar.pro.dialogs

import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.calendar.pro.R
import com.simplemobiletools.calendar.pro.activities.SimpleActivity
import com.simplemobiletools.calendar.pro.adapters.FilterEventTypeAdapter
import com.simplemobiletools.calendar.pro.extensions.config
import com.simplemobiletools.calendar.pro.helpers.EventsHelper
import com.simplemobiletools.commons.extensions.setupDialogStuff
import kotlinx.android.synthetic.main.dialog_filter_event_types.view.*

class FilterEventTypesDialog(val activity: SimpleActivity, val callback: () -> Unit) {
    private lateinit var dialog: AlertDialog
    private val view = activity.layoutInflater.inflate(R.layout.dialog_filter_event_types, null)

    init {
        EventsHelper(activity).getEventTypes(activity) {
            val displayEventTypes = activity.config.displayEventTypes
            view.filter_event_types_list.adapter = FilterEventTypeAdapter(activity, it, displayEventTypes)

            dialog = AlertDialog.Builder(activity)
                    .setPositiveButton(R.string.ok) { dialogInterface, i -> confirmEventTypes() }
                    .setNegativeButton(R.string.cancel, null)
                    .create().apply {
                        activity.setupDialogStuff(view, this, R.string.filter_events_by_type)
                    }
        }
    }

    private fun confirmEventTypes() {
        val selectedItems = (view.filter_event_types_list.adapter as FilterEventTypeAdapter).getSelectedItemsSet()
        if (activity.config.displayEventTypes != selectedItems) {
            activity.config.displayEventTypes = selectedItems
            callback()
        }
        dialog.dismiss()
    }
}
