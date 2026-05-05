package com.example.dictionary_inz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dictionary_inz.ui.details.ItemDetailsView
import com.example.dictionary_inz.ui.home.HomeView
import com.example.dictionary_inz.ui.learn.LearnSelectionView
import com.example.dictionary_inz.ui.learn.flashcards.FlashcardView
import com.example.dictionary_inz.ui.learn.quiz.QuizView

object NavRoutes {
    const val HOME = "home"
    const val ITEM_DETAILS = "itemDetails"
    const val LEARN_SELECTION = "learnSelection"
    const val QUIZ = "quiz"
    const val FLASHCARDS = "flashcards"
}

@Composable
fun DictionaryNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(NavRoutes.HOME) {
            HomeView(
                onNavigateToItemDetails = { wordId ->
                    navController.navigate("${NavRoutes.ITEM_DETAILS}/$wordId")
                },
                onNavigateToLearnSelection = {
                    navController.navigate(NavRoutes.LEARN_SELECTION)
                }
            )
        }

        composable(
            route = "${NavRoutes.ITEM_DETAILS}/{wordId}",
            arguments = listOf(navArgument("wordId") { type = NavType.IntType })
        ) { backStackEntry ->
            val wordId = backStackEntry.arguments?.getInt("wordId")
            if (wordId != null) {
                ItemDetailsView(
                    wordId = wordId,
                    onBackClick = { navController.popBackStack() },
                    onWordDeleted = { navController.popBackStack() }
                )
            }
        }

        composable(NavRoutes.LEARN_SELECTION) {
            LearnSelectionView(
                onNavigateToQuiz = { navController.navigate(NavRoutes.QUIZ) },
                onNavigateToFlashcards = { navController.navigate(NavRoutes.FLASHCARDS) },
                onBackClick = {
                    navController.navigate(NavRoutes.HOME) { popUpTo(NavRoutes.HOME) { inclusive = true } }
                },
                onReturnHome = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.QUIZ) {
            QuizView(
                onBackClick = { navController.navigate(NavRoutes.LEARN_SELECTION) },
                onReturnHome = {
                    navController.navigate(NavRoutes.HOME) { popUpTo(NavRoutes.HOME) { inclusive = true } }
                }
            )
        }

        composable(NavRoutes.FLASHCARDS) {
            FlashcardView(
                onBackClick = { navController.navigate(NavRoutes.LEARN_SELECTION) },
                onReturnHome = {
                    navController.navigate(NavRoutes.HOME) { popUpTo(NavRoutes.HOME) { inclusive = true } }
                }
            )
        }
    }
}