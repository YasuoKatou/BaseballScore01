### (1) チームメンバーテーブル（team_member）

|No|和名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|メンバーID|member_id|long||1|no||
|2|性|name1|verchar|64||no||
|3|名|name2|verchar|64||no||
|4|性別|sex|int|||no|1：男、2：女|
|5|誕生日|birthday|date|||no||
|6|登録日時|new_date_time|date_time|||no||
|7|更新日時|upd_date_time|date_time|||no||
|8|バージョンNo|verno|int|||no||
|9||||||||
|10||||||||

### (2) 試合情報テーブル（game_info）
|No|和名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|ゲームID|game_id|long||1|no||
|2|試合名|game_name|varchar|256||no||
|3|試合日|game_date|date|||no||
|4|開始時刻|start_time|time|||||
|5|終了時刻|end_time|time|||||
|6|表裏|top_bottom|int|||no|1：表、2：裏|
|7|対戦チーム名|competition_team_name|varchar|256||||
|8|審判（主）|umpire1|varchar|64||||
|9|審判２|umpire2|varchar|64||||
|10|審判３|umpire3|varchar|64||||
|11|審判４|umpire4|varchar|64||||
|12|審判５|umpire5|varchar|64||||
|13|審判６|umpire6|varchar|64||||
|14|登録日時|new_date_time|date_time|||no||
|15|更新日時|upd_date_time|date_time|||no||
|16|バージョンNo|verno|int|||no||
|17||||||||
|18||||||||
|19||||||||
|20||||||||

### (3) 試合結果テーブル（game_result）
|No|和名|物理名|型|サイズ|PK|null|備考|
|:-:|:--|:--|:-:|--:|:-:|:-:|:--|
|1|ゲームID|game_id|long||1|no||
|2|イニング|inning|int||2|no||
|3|表裏|top_bottom|int||3|no|1：表、2：裏|
|4|点数|score|int|||no||
|5|登録日時|new_date_time|date_time|||no||
|6|更新日時|upd_date_time|date_time|||no||
|7|バージョンNo|verno|int|||no||
|8||||||||
|9||||||||
|10||||||||
