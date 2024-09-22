package com.embrodev.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

//Импорт списков участников команд
import static com.embrodev.Commands.setTeam.attack;
import static com.embrodev.Commands.setTeam.defense;

public class listTeam implements CommandExecutor {
    private Player p;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        p = (Player) commandSender;

        //Проверка на синтаксис
        if(args.length < 1){
            p.sendMessage("Пожалуйста, введите команду");
            return false;
        } else if (!(args[0].equals("attack") || args[0].equals("defense") )) {
            p.sendMessage("Укажите команду в правильном формате!(attack, defense)");
            return false;
        }

        //Вывод ников участников команды атаки. Список attackNames нужен, поскольку список attack является типом Player, и не может быть выведен через String.join
        if(args[0].equals("attack")){
            ArrayList<String> attackNames = new ArrayList<String>();
            //Получаем String значения ников из списка attack и записываем в attackNames
            for (int i = 0; i < attack.size(); i++) {
                attackNames.add(attack.get(i).getName());
            }
            //Выводим список attackNames, чередуя каждый ник через запятую
            p.sendMessage(String.join(", ", attackNames));
        } else{
            //Аналогично с прошлым пояснением
            ArrayList<String> defenseNames = new ArrayList<String>();
            for (int i = 0; i < defense.size(); i++) {
                defenseNames.add(defense.get(i).getName());
            }
            p.sendMessage(String.join(", ", defenseNames));
        }


        return true;
    }
}
