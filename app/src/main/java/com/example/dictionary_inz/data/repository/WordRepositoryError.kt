package com.example.dictionary_inz.data.repository

//sealed class WordRepositoryError : Exception() {
//   data object NoInternet : WordRepositoryError() {
//        override val message = "Brak internetu."
//        private fun readResolve(): Any = NoInternet
//    }
//
//   data object ServerError : WordRepositoryError() {
//        override val message = "Serwer nie mógł zrealizować twojego żądania."
//        private fun readResolve(): Any = ServerError
//    }
//
//    data class WordNotFound(val word: String) : WordRepositoryError() {
//        override val message = "Nieznane słowo \"$word\""
//    }
//
//    data object UnknownError : WordRepositoryError() {
//        override val message = "Wystąpił nieoczekiwany błąd."
//        private fun readResolve(): Any = UnknownError
//    }
//
//    data object SingleWordOnlyError : WordRepositoryError() {
//        override val message = "Słownik obsługuje pojedyncze słowa"
//        private fun readResolve(): Any = SingleWordOnlyError
//    }
//}




sealed class WordRepositoryError : Exception() {
    data object NoInternet : WordRepositoryError() {
        private fun readResolve(): Any = NoInternet
    }

    data object ServerError : WordRepositoryError() {
        private fun readResolve(): Any = ServerError
    }

    data class WordNotFound(val word: String) : WordRepositoryError()

    data object UnknownError : WordRepositoryError() {
        private fun readResolve(): Any = UnknownError
    }

    data object SingleWordOnlyError : WordRepositoryError() {
        private fun readResolve(): Any = SingleWordOnlyError
    }
}




