package com.indigo.woorm.users.service

import io.ktor.resources.Resource

@Resource("users")
class Users {
    @Resource("{id}")
    data class Id(val parent: Users = Users(), val id: String)
}