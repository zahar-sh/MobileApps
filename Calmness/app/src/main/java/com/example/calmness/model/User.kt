package com.example.calmness.model

class User(
    var id: String,
    var photoUrl: String,
    var name: String,
    var email: String,
    var age: String,
    var weight: String,
    var arteriotony: String,
    var phone: String
) {
    constructor() : this("", "", "", "", "", "", "", "")
}