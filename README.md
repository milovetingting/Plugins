>  上传Plugin和上传AAR的gradle命令有冲突，需要注释掉其中一个

## 上传Plugin到Bintray

```shell
gradlew clean build bintrayUpload -PbintrayUser=UserName -PbintrayKey=Key -PdryRun=false
```

## 上传AAR到Bintray

先执行

```shell
gradlew install
```

再执行

```shell
gradlew bintrayUpload
```

