package gli_

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec


class coreFormat : StringSpec() {

    init {

        "valid" {

            for (formatIndex in FORMAT_FIRST.i until FORMAT_COUNT)
                Format.values().first { it.i == formatIndex }.isValid shouldBe true
            Format.INVALID.isValid shouldBe false
        }

        "component" {

            for (formatIndex in FORMAT_FIRST.i until FORMAT_COUNT) {
                val components = Format.values().first { it.i == formatIndex }.componentCount
                (components in 1..4) shouldBe true
            }
        }

        "compressed" {

            Format.R8_SRGB_PACK8.isCompressed shouldBe false
            Format.RGB_DXT1_SRGB_BLOCK8.isCompressed shouldBe true
        }

        "block" {
            Format.RGBA8_UNORM_PACK8.blockSize shouldBe 4
            Format.RGB10A2_UNORM_PACK32.blockSize shouldBe 4
        }
    }
}