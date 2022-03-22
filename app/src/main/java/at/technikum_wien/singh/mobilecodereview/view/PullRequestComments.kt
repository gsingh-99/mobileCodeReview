package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCComment
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PullRequestComments(viewModel: CodeReviewViewModel, comment: VSCComment) {
    Box(Modifier.padding(0.dp, 6.dp)) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(comment.user.avatar_url)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_baseline_account_circle_24),
                contentDescription = "user_pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(24.dp)
            )
            Text(
                text = " ${comment.user.login}: ",
                color = Color.Black,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = "${comment.body}",
                color = Color.Black,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = viewModel.calcUpdateDuration(comment.updated_at),
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}