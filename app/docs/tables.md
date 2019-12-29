### (1) チームメンバーテーブル（team_member）

|No|和名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|メンバーID|member_id|integer||1|no|自動採番|
|2|性|name1|text|64||no||
|3|名|name2|text|64||no||
|4|性別|sex|int|||no|1：男、2：女|
|5|誕生日|birthday|int|||no||
|6|登録日時|new_date_time|int|||no||
|7|更新日時|upd_date_time|int|||no||
|8|バージョンNo|verno|int|||no||
|9||||||||
|10||||||||

### (2) 試合情報テーブル（game_info）
|No|和名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|ゲームID|game_id|integer||1|no|自動採番|
|2|試合名|game_name|text|256||no||
|3|試合日|game_date|int|||no||
|4|開始時刻|start_time|int|||||
|5|終了時刻|end_time|int|||||
|6|表裏|top_bottom|int|||no|1：表、2：裏|
|7|対戦チーム名|competition_team_name|text|256||||
|8|審判（主）|umpire1|text|64||||
|9|審判２|umpire2|text|64||||
|10|審判３|umpire3|text|64||||
|11|審判４|umpire4|text|64||||
|12|審判５|umpire5|text|64||||
|13|審判６|umpire6|text|64||||
|14|登録日時|new_date_time|int|||no||
|15|更新日時|upd_date_time|int|||no||
|16|バージョンNo|verno|int|||no||
|17||||||||
|18||||||||
|19||||||||
|20||||||||

### (3) 試合結果テーブル（game_result）
|No|和名|物理名|型|サイズ|PK|null|備考|
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
