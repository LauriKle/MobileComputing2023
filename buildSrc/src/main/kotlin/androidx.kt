object androidx {
    object core {
        private val version = "1.9.0"
        val ktx = "androidx.core:core-ktx:$version"
    }
    object compose {
        private val version = "1.1.1"
        object ui{
            val ui = "androidx.compose.ui:ui:$version"
            val preview = "androidx.compose.ui:ui-tooling-preview:$version"
        }
        val material = "androidx.compose.material:material:$version"

    }
    object lifecycle {
        private val version = "1.9.0"
    }

    object activity {
        private val version = "1.9.0"
    }
}