@echo off
    REM Gradle Wrapper for Windows
    set DIR=%~dp0
    java -Xmx1024m -Dfile.encoding=UTF-8 -classpath "%DIR%gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
    