package com.lau.google_rep.data

import java.security.acl.Owner

//data class for the repository

data class repo(val name: String, val owner: owner, val created_at: String, val stargazers_count: String) {
    constructor() : this("",owner(),"","")
}