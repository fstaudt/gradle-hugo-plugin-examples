plugins {
    id("io.github.fstaudt.hugo") version "0.10.0"
}

hugo {
    // required for compatibility with Hugo whisper theme
    // cfr https://github.com/zerostaticthemes/hugo-whisper-theme/issues/43
    version = "0.131.0"
}
