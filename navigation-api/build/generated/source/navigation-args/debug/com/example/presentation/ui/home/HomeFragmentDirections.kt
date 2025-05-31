package com.example.presentation.ui.home

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.navigation.api.R
import kotlin.Int

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeFragmentToMovieDetailsFragment(
    public val movieId: Int,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_movieDetailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("movieId", this.movieId)
        return result
      }
  }

  public companion object {
    public fun actionHomeFragmentToMovieDetailsFragment(movieId: Int): NavDirections =
        ActionHomeFragmentToMovieDetailsFragment(movieId)
  }
}
