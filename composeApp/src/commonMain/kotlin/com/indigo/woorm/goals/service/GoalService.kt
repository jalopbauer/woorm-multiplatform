package com.indigo.woorm.goals.service

import com.indigo.woorm.goals.GoalPreview
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone

class GoalService(private val client: HttpClient) {

    suspend fun goals(): List<GoalPreview> =
        client.get(Goals()).body()

    suspend fun currentGoal(): GoalPreview? =
        client.get(Goals.Current()).takeIf { it.status == HttpStatusCode.OK }?.body()

    suspend fun set(id: Int, amount: Int) {
        client.put(Goals.Id(id = id)) {
            setBody(UpdateGoal(amount = amount))
        }
    }
    suspend fun create(amount: Int) {
        client.post(Goals()) {
            setBody(
                CreateGoal(
                    amount = amount,
                    year = Clock.System.todayIn(TimeZone.currentSystemDefault()).year
                )
            )
        }
    }

    suspend fun delete(id: Int) {
        client.delete(Goals.Id(id = id))
    }
}

@Resource("goals")
class Goals {
    @Resource("{id}")
    data class Id(val parent: Goals = Goals(), val id: Int)
    @Resource("current")
    data class Current(val parent: Goals = Goals())
}