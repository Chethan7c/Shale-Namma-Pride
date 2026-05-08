// Shale-Namma Pride - Jetpack Compose Component Library
// File: app/src/main/java/com/shalenamma/pride/ui/components/ShaleNammaComponents.kt

package com.shalenamma.pride.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import com.shalenamma.pride.ui.theme.Colors
import com.shalenamma.pride.ui.theme.LayoutDimensions
import com.shalenamma.pride.ui.theme.Spacing
import androidx.compose.foundation.BorderStroke
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.res.stringResource
import com.shalenamma.pride.R

// ============================================================================
// PRIMARY BUTTON
// ============================================================================

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    fullWidth: Boolean = true
) {
    val haptic = LocalHapticFeedback.current
    Button(
        onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); onClick() },
        enabled = enabled && !isLoading,
        modifier = modifier
            .height(LayoutDimensions.ButtonHeight)
            .animateContentSize(animationSpec = spring())
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
        } else {
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

// ============================================================================
// SECONDARY BUTTON
// ============================================================================

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fullWidth: Boolean = false
) {
    val haptic = LocalHapticFeedback.current
    FilledTonalButton(
        onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); onClick() },
        enabled = enabled,
        modifier = modifier
            .height(LayoutDimensions.ButtonHeight)
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

// ============================================================================
// TEXT BUTTON
// ============================================================================

@Composable
fun TextButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(LayoutDimensions.ButtonHeight),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Colors.TrustBlue,
            disabledContentColor = Colors.MediumGray
        )
    ) {
        Text(text)
    }
}

// ============================================================================
// EMAIL INPUT FIELD
// ============================================================================

@Composable
fun EmailInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(LayoutDimensions.InputFieldHeight),
            placeholder = { Text("Enter your email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon",
                    tint = if (isError) Colors.ErrorRed else Colors.MediumGray
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = isError,
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Colors.TrustBlue,
                unfocusedBorderColor = Colors.LightGrayBorder,
                errorBorderColor = Colors.ErrorRed,
                focusedLabelColor = Colors.TrustBlue,
                errorCursorColor = Colors.ErrorRed
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Colors.ErrorRed,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = Spacing.xs)
            )
        }
    }
}

// ============================================================================
// PASSWORD INPUT FIELD
// ============================================================================

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    placeholder: String = "Enter your password"
) {
    val isPasswordVisible = remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(LayoutDimensions.InputFieldHeight),
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon",
                    tint = if (isError) Colors.ErrorRed else Colors.MediumGray
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible.value = !isPasswordVisible.value }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible.value) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = "Toggle password visibility",
                        tint = Colors.MediumGray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPasswordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = isError,
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Colors.TrustBlue,
                unfocusedBorderColor = Colors.LightGrayBorder,
                errorBorderColor = Colors.ErrorRed,
                focusedLabelColor = Colors.TrustBlue,
                errorCursorColor = Colors.ErrorRed
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Colors.ErrorRed,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = Spacing.xs)
            )
        }
    }
}

// ============================================================================
// GENERIC INPUT FIELD
// ============================================================================

@Composable
fun ShaleNammaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = Spacing.sm)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            isError = isError,
            enabled = enabled,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Colors.TrustBlue,
                unfocusedBorderColor = Colors.LightGrayBorder,
                errorBorderColor = Colors.ErrorRed,
                focusedLabelColor = Colors.TrustBlue,
                errorCursorColor = Colors.ErrorRed
            )
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Colors.ErrorRed,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = Spacing.xs)
            )
        }
    }
}

// ============================================================================
// CARD COMPONENT
// ============================================================================

@Composable
fun ShaleNammaCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = spring())
            .then(
                if (onClick != null) Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(LayoutDimensions.CardPadding)) {
            content()
        }
    }
}

// ============================================================================
// BADGE/CHIP COMPONENT
// ============================================================================

@Composable
fun ShaleNammaBadge(
    text: String,
    backgroundColor: Color = Colors.TrustBlue,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = Spacing.sm, vertical = Spacing.xs),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}

// ============================================================================
// LOADING INDICATOR
// ============================================================================

@Composable
fun ShaleNammaLoadingIndicator(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 48.dp
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = Colors.TrustBlue,
        strokeWidth = 4.dp
    )
}

// ============================================================================
// EMPTY STATE
// ============================================================================

@Composable
fun EmptyStateCard(
    iconText: String,
    title: String,
    subtitle: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = iconText,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = Spacing.lg)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = Spacing.sm)
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Colors.MediumGray,
            modifier = Modifier.padding(bottom = Spacing.lg)
        )
        if (actionText != null && onActionClick != null) {
            PrimaryButton(
                text = actionText,
                onClick = onActionClick,
                fullWidth = true
            )
        }
    }
}

// ============================================================================
// DIVIDER COMPONENT
// ============================================================================

@Composable
fun ShaleNammaDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: Color = Colors.LightGrayBorder
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(color)
    )
}

// ============================================================================
// SECTION HEADER
// ============================================================================

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.headlineSmall,
        color = Colors.TrustBlue,
        modifier = modifier.padding(
            vertical = Spacing.md,
            horizontal = Spacing.md
        )
    )
    ShaleNammaDivider(modifier = Modifier.padding(horizontal = Spacing.md))
}

// ============================================================================
// RATING BAR
// ============================================================================

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int = 5,
    onRatingChanged: (Float) -> Unit = {},
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) { index ->
            Text(
                text = "★",
                style = MaterialTheme.typography.headlineSmall,
                color = if (index < rating.toInt()) Colors.WarningOrange else Colors.LightGrayBorder,
                modifier = Modifier
                    .size(24.dp)
                    .then(
                        if (!readOnly) {
                            Modifier.clickable {
                                onRatingChanged((index + 1).toFloat())
                            }
                        } else {
                            Modifier
                        }
                    )
            )
        }
        Text(
            text = "($rating/$maxRating)",
            style = MaterialTheme.typography.bodySmall,
            color = Colors.MediumGray,
            modifier = Modifier.padding(start = Spacing.sm)
        )
    }
}

// ============================================================================
// CHECKBOX WITH LABEL
// ============================================================================

@Composable
fun CheckboxWithLabel(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material3.CheckboxDefaults.colors(
                checkedColor = Colors.TrustBlue,
                uncheckedColor = Colors.LightGrayBorder
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = Spacing.sm)
        )
    }
}

// ============================================================================
// INFO CARD (with icon + text)
// ============================================================================

@Composable
fun InfoCard(
    iconText: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Colors.LightGray,
    onClick: (() -> Unit)? = null
) {
    ShaleNammaCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconText,
                    style = MaterialTheme.typography.displayMedium
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Spacing.md)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Colors.MediumGray,
                    modifier = Modifier.padding(top = Spacing.xs)
                )
            }
        }
    }
}

// ============================================================================
// LIST ITEM WITH LEADING IMAGE
// ============================================================================

@Composable
fun ImageListItem(
    imageUrl: String,
    title: String,
    subtitle: String,
    trailingText: String? = null,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    ShaleNammaCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for image (actual image loading handled by caller)
            Box(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .background(Colors.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🖼️", style = MaterialTheme.typography.displayLarge)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Spacing.md)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Colors.MediumGray,
                    modifier = Modifier.padding(top = Spacing.xs)
                )
                if (trailingText != null) {
                    Text(
                        text = trailingText,
                        style = MaterialTheme.typography.labelSmall,
                        color = Colors.MediumGray,
                        modifier = Modifier.padding(top = Spacing.xs)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController, currentRoute: String) {
    NavigationBar(
        containerColor = Colors.White,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home), modifier = Modifier.size(24.dp)) },
            label = { Text(stringResource(R.string.home), style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Restaurant, contentDescription = stringResource(R.string.meals), modifier = Modifier.size(24.dp)) },
            label = { Text(stringResource(R.string.meals), style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "meals",
            onClick = { navController.navigate("meals") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Image, contentDescription = stringResource(R.string.gallery), modifier = Modifier.size(24.dp)) },
            label = { Text(stringResource(R.string.gallery), style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "gallery",
            onClick = { navController.navigate("gallery") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = stringResource(R.string.achievements), modifier = Modifier.size(24.dp)) },
            label = { Text(stringResource(R.string.achievements), style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "achievements",
            onClick = { navController.navigate("achievements") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = stringResource(R.string.profile), modifier = Modifier.size(24.dp)) },
            label = { Text(stringResource(R.string.profile), style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") }
        )
    }
}

@Composable
fun CurvedTopBar(content: @Composable () -> Unit) {
    androidx.compose.material3.Surface(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        color = Colors.White,
        shadowElevation = 4.dp
    ) {
        content()
    }
}

// ============================================================================
// FULL SCREEN IMAGE VIEWER
// ============================================================================

@Composable
fun FullScreenImageViewer(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.95f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(Spacing.md)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Image(
                painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    contentScale = ContentScale.Fit
                ),
                contentDescription = "Full screen image",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(Spacing.md),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(Spacing.lg))
        }
    }
}
