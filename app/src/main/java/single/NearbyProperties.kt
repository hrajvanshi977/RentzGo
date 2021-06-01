package single

import com.india.rentzgo.utils.PropertiesAndDistance

object NearbyProperties {
    var index : Int = 0
    var list = ArrayList<PropertiesAndDistance>()

    fun clearAllProperties() {
        this.list.clear()
    }
}