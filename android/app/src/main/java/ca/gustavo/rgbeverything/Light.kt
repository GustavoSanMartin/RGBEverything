package ca.gustavo.rgbeverything

data class Light(
    var id: String = "default_id",
    var name: String = "default_name",
    var brightness: Int = 100,
    var color: String = "FFFFFF"
)