### (1) チームメンバーテーブル（team_member）

|No|論理名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|メンバーID|member_id|integer||1|no|自動採番|
|2|性|name1|text|64||no||
|3|名|name2|text|64||no||
|4|性別|sex|int|||no|1：男、2：女|
|5|誕生日|birthday|long|||no||
|6|ポジション|position_category|int|||||
|7|投|pitching|int|||||
|8|打|batting|int|||||
|9|状態|status|int|||no|団員 卒団 中退|
|10|登録日時|new_date_time|long|||no||
|11|更新日時|upd_date_time|long|||no||
|12|バージョンNo|verno|int|||no||
|13||||||||
|14||||||||
|15||||||||

### (2) 試合情報テーブル（game_info）
|No|論理名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|ゲームID|game_id|integer||1|no|自動採番|
|2|試合名|game_name|text|256||no||
|3|試合場所|place|text|||||
|4|試合日|game_date|long|||no||
|5|開始時刻|start_time|long|||||
|6|終了時刻|end_time|long|||||
|7|表裏|top_bottom|int|||no|1：表、2：裏|
|8|対戦チーム名|competition_team_name|text|256||||
|9|審判（主）|umpire1|text|64||||
|10|審判２|umpire2|text|64||||
|11|審判３|umpire3|text|64||||
|12|審判４|umpire4|text|64||||
|13|審判５|umpire5|text|64||||
|14|審判６|umpire6|text|64||||
|15|登録日時|new_date_time|long|||no||
|16|更新日時|upd_date_time|long|||no||
|17|バージョンNo|verno|int|||no||
|18||||||||
|19||||||||
|20||||||||

### (4) スターティングメンバ―テーブル（game_starting_member）
|No|論理名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|ゲームID|game_id|int||1|no||
|2|打順|batting_order|int||2|no|1～9|
|3|メンバーID|member_id|integer|||no||
|4|ポジション|position|int|||||
|5||||||||
|6||||||||
|7||||||||
|8||||||||
|9||||||||
|810||||||||


### (4) 試合結果テーブル（game_result）
|No|論理名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|ゲームID|game_id|int||1|no||
|2|イニング|inning|int||2|no||
|3|表裏|top_bottom|int||3|no|1：表、2：裏|
|4|点数|score|int|||no||
|5|登録日時|new_date_time|int|||no||
|6|更新日時|upd_date_time|int|||no||
|7|バージョンNo|verno|int|||no||
|8||||||||
|9||||||||
|10||||||||
