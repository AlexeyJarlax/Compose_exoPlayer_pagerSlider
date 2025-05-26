package com.pavlovalexey.startsetupforcomposein2024.repository

import android.content.Context
import com.pavlovalexey.startsetupforcomposein2024.model.Workout
import com.pavlovalexey.startsetupforcomposein2024.model.WorkoutDao
import com.pavlovalexey.startsetupforcomposein2024.network.WorkoutApiService
import com.pavlovalexey.startsetupforcomposein2024.network.WorkoutDto
import com.pavlovalexey.startsetupforcomposein2024.utils.loadImageUrlsFromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.yaml.snakeyaml.Yaml
import java.io.InputStreamReader
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val api: WorkoutApiService,
    @ApplicationContext private val context: Context,
    private val dao: WorkoutDao
) : WorkoutRepository {

    override suspend fun getWorkouts(): List<Workout> = withContext(Dispatchers.IO) {
        val imageUrls = loadImageUrlsFromAssets(context)

        try {
            val dtos = api.getWorkouts()
            val workouts = dtos.map { dto ->
                val base = mapDtoToWorkout(dto)
                base.copy(imageUrl = base.imageUrl ?: imageUrls.randomOrNull())
            }
            dao.clear()
            dao.insertAll(workouts)
            workouts
        } catch (e: Exception) {
            dao.getAll().takeIf { it.isNotEmpty() }?.let { return@withContext it }
            val local = loadLocalFromYaml().map { w ->
                w.copy(imageUrl = imageUrls.randomOrNull())
            }
            dao.clear()
            dao.insertAll(local)
            local
        }
    }

    private fun mapDtoToWorkout(dto: WorkoutDto): Workout {
        val mins = dto.duration.substringBefore(" ").toIntOrNull() ?: 0
        val typeName = when (dto.type) {
            1 -> "Тренировка"
            2 -> "Эфир"
            3 -> "Комплекс"
            else -> "Другое"
        }
        return Workout(
            id          = dto.id,
            title       = dto.title,
            description = dto.description ?: "Описание недоступно",
            type        = typeName,
            duration    = mins,
            imageUrl    = null
        )
    }

    private fun loadLocalFromYaml(): List<Workout> {
        val input = context.assets.open("server.yaml")
        val root  = Yaml().load<Map<String, Any>>(InputStreamReader(input))

        @Suppress("UNCHECKED_CAST")
        val paths           = root["paths"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val getWorkoutsNode = paths["/get_workouts"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val getNode         = getWorkoutsNode["get"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val responses       = getNode["responses"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val resp200         = responses["200"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val content         = resp200["content"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val appJson         = content["application/json"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val examples        = appJson["examples"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val example1        = examples["example-1"] as Map<String, Any>
        @Suppress("UNCHECKED_CAST")
        val list            = example1["value"] as List<Map<String, Any>>

        return list.map { m ->
            val id    = (m["id"] as Number).toInt()
            val title = m["title"] as String
            val desc  = m["description"] as? String ?: "Описание недоступно"
            val type  = when ((m["type"] as Number).toInt()) {
                1 -> "Тренировка"
                2 -> "Эфир"
                3 -> "Комплекс"
                else -> "Другое"
            }
            val mins  = (m["duration"] as String).substringBefore(" ").toIntOrNull() ?: 0
            Workout(id, title, desc, type, mins, null)
        }
    }

    override suspend fun getWorkoutById(id: Int): Workout =
        dao.getById(id) ?: throw Exception("Тренировка с id=$id не найдена")

    override suspend fun getWorkoutVideoLink(id: Int): String =
        api.getVideo(id).link
}