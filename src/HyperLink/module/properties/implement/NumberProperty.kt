package HyperLink.module.properties.implement

import HyperLink.module.properties.Property

class NumberProperty(name: String?, value: Double, val minimum: Double, val maximum: Double, val increment: Double) : Property<Double?>(name, value) {

}
