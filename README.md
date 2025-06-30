# et-common
Employment Tribunals Common library

Re-usable features for all Employment Tribunals Java Projects

### Prerequisites

- [JDK 21](https://www.oracle.com/java)

## Usage

The plugin is hosted on [Azure Artifacts](https://hmcts.github.io/cloud-native-platform/common-pipeline/publishing-libraries/java.html) so you must add the following to your project's Gradle file as a repository:

```gradle
  maven {
    url 'https://pkgs.dev.azure.com/hmcts/Artifacts/_packaging/hmcts-lib/maven/v1'
  }
```

## Building

The project uses [Gradle](https://gradle.org) as a build tool but you don't have install it locally since there is a
`./gradlew` wrapper script.  

To build project please execute the following command:

```bash
    ./gradlew build
```

## Developing

### Coding style tests

To run all checks (including unit tests) please execute the following command:

```bash
    ./gradlew check
```

## Publishing

The GitHub workflow located in `.github/workflows/ado_artifacts_build.yml` publishes the library to Azure Artifacts automatically:

- **Master Branch**: A new tag is created with an incremented version number, used for publishing.
- **Pull Requests**: The version is suffixed with `<branch-name>-<commit-sha>` (e.g., RET-0000-1fa21d1).

To publish a test version locally, run:

```bash
./gradlew publishToMavenLocal
```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
