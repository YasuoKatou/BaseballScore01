package jp.yksolution.android.app.baseballscore01;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.util.List;

import jp.yksolution.android.app.baseballscore01.ui.common.PopupMenuOperation;
import jp.yksolution.android.app.baseballscore01.db.DbHelper;
import jp.yksolution.android.app.baseballscore01.ui.member.MemberFragment;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            showSnackbar(view);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_member, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // DBの作成／更新を行う
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * ポップアップメニューが開く時のイベント
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR && menu != null) {
            // このクラスでは、一旦全てのメニューグループを非表示にする
            Fragment fragment = this.getActiveFragment();
            // TODO 動的に処理したい！！
            menu.setGroupVisible(R.id.optMenuTeamMember, false);
//            menu.setGroupVisible(R.id.optMenuOthers, false);      // 最低１つは表示しないとアイコンが消える
            // 各フラグメントで使用するメニュを表示する
            if (fragment instanceof PopupMenuOperation) {
                PopupMenuOperation popupMenu = (PopupMenuOperation)fragment;
                popupMenu.setupMenu(menu);
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {

    }

    private Fragment getActiveFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            Fragment activeFragment = fragment.getChildFragmentManager().getPrimaryNavigationFragment();
            Log.d(TAG, "Fragment : " + activeFragment.toString());
            return activeFragment;
        }
        return null;
    }

    private void showSnackbar(View view) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            Fragment activeFragment = fragment.getChildFragmentManager().getPrimaryNavigationFragment();
            Log.d(TAG, "Fragment : " + activeFragment.toString());
            if (activeFragment instanceof MemberFragment) {
                this.showSnackbarForMember(view);
            } else {
                Log.e(TAG, "フラグメント毎の処理を実装");
            }
        }
    }

    private void showSnackbarForMember(View view) {
        Snackbar.make(view, "TODO add member actions", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
    }
}