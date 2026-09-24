package com.example.weighttracker.data.local.structure

import com.example.weighttracker.data.local.WeightEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.TreeMap

// This class implements BST and replaces list sorting as part of my second enhancement
class WeightBST {
    // Binary search tree sorted by date
    private val tree = TreeMap<LocalDate, WeightEntry>()
    private val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    fun insert(entry: WeightEntry) {
        val localDate = LocalDate.parse(entry.date, formatter)
        tree[localDate] = entry
    }

    fun getLastEntry(): WeightEntry? = tree.lastEntry()?.value

    fun size(): Int = tree.size

    // Retrieve only last N days rather than doing a full history scan
    fun getLastNDays(n: Int): List<WeightEntry> {
        if (tree.isEmpty()) return emptyList()
        // Get last N values from ordered map without full scan
        return tree.descendingMap().values.take(n).toList().reversed()
    }

    // This method calculates weight difference using first and last entries, now using direct BST lookups
    fun getWeightDifference(): Float {
        if (size() < 2) return 0f
        val firstWeight = tree.firstEntry()?.value?.weight?.toFloat() ?: 0f
        val lastWeight = tree.lastEntry()?.value?.weight?.toFloat() ?: 0f
        return lastWeight - firstWeight
    }
}