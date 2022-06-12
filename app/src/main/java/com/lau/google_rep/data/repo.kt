package com.lau.google_rep.data

import java.security.acl.Owner

//data class for the repository

data class repo(var id:String? = "",var name: String = "", var owner: owner = owner(), var created_at: String = "", var stargazers_count: String = "") {

}