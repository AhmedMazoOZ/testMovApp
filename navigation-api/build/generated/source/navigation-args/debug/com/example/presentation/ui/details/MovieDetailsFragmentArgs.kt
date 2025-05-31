package com.example.presentation.ui.details

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class MovieDetailsFragmentArgs(
  public val movieId: Int,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("movieId", this.movieId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("movieId", this.movieId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MovieDetailsFragmentArgs {
      bundle.setClassLoader(MovieDetailsFragmentArgs::class.java.classLoader)
      val __movieId : Int
      if (bundle.containsKey("movieId")) {
        __movieId = bundle.getInt("movieId")
      } else {
        throw IllegalArgumentException("Required argument \"movieId\" is missing and does not have an android:defaultValue")
      }
      return MovieDetailsFragmentArgs(__movieId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MovieDetailsFragmentArgs {
      val __movieId : Int?
      if (savedStateHandle.contains("movieId")) {
        __movieId = savedStateHandle["movieId"]
        if (__movieId == null) {
          throw IllegalArgumentException("Argument \"movieId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"movieId\" is missing and does not have an android:defaultValue")
      }
      return MovieDetailsFragmentArgs(__movieId)
    }
  }
}
