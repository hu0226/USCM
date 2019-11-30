package com.scu.uscm.database.local

import androidx.room.TypeConverter
import com.scu.uscm.database.model.Schedule
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    /**
     * Convert [List] [String] to Json
     */
    @TypeConverter
    fun convertListToJson(list: List<String>?): String? {
        list?.let {
            return Moshi.Builder().build().adapter<List<String>>(List::class.java).toJson(list)
        }
        return null
    }

    /**
     * Convert Json to [List] [String]
     */
    @TypeConverter
    fun convertJsonToList(json: String?): List<String>? {
        json?.let {
            val type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }

    /**
     * Convert [Place] to Json
     */
    @TypeConverter
    fun convertPlaceToJson(place: Schedule?): String? {
        place?.let {
            return Moshi.Builder().build().adapter(Schedule::class.java).toJson(place)
        }
        return null
    }

    /**
     * Convert Json to [Place]
     */
    @TypeConverter
    fun convertJsonToPlace(json: String?): Schedule? {
        json?.let {
            val type = Types.newParameterizedType(Schedule::class.java)
            val adapter: JsonAdapter<Schedule> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }
}