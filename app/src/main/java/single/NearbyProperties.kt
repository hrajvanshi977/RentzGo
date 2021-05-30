package single

import com.india.rentzgo.utils.PropertiesAndDistance

object NearbyProperties {
    var list = ArrayList<PropertiesAndDistance>()

    fun clearAllProperties() {
        this.list.clear()
    }
}