# TheMovieDB
A demo app which uses API provided by [The Movie DB](https://developers.themoviedb.org/4) to show
a list of movies and its details.

## Setup
Below are the requirements needed to work with this codebase.

### System Requirements
- Android Studio Dolphin, since this project uses AGP 7.3.0 (> 7.2.0).
- CMake version 3.22.1

### Update pre-hook path
`/tools` contains shared pre-hooks for certain checks & validations.
To change your local hooks path, need to run following command:

```
git config --local core.hooksPath tools/
```