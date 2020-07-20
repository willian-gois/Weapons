# Weapons

Plugin para a criação de armas com características especiais. São separadas em seis raridades *(comum, incomum, raro, épico, mítico, lendário)*.
*Com seu desenvolvimento dois a três dias, foi utilizado até o fechamento dos servidores do FinalElite.*

**Autor do código: Willian Gois / zMathi**

## Lista de armas exemplares
Cada categoria tem no mínimo seis armas diferentes, uma para cada raridade.

### Espadas
- Espada Cega (Incomum) - Chance de dar cegueira ao inimigo por 3 segundos. *[+0.25 de chance a cada nível]*

### Machados
- Machado Viking (Mítico) - Chance de causar o dobro de dano em armaduras. *[+0.30 de chance a cada nível]*

### Capacetes
- Capacete Vulcânico (Raro) - Chance de atirar bolas de fogo para todos os lados. *[+0.15 de chance por nível]*

### Peitorais
- Peitoral Mágico (Mítico) - Chance de anular efeitos de outras armas. *[+0.25% de chance a cada nível]*

### Calças
- Calça de Fuga (Épico) - Chance de ser montado numa enderpear e fugir. *[+0.30% de chance a cada nível]*

### Botas
- Botas de Água (Comum) - Chance de não ficar em fogo caso o inimigo tenha aspecto flamejante. *[+0.40% de chance por nível]*

## Como usar
Usando a API do plugin é possivel criar e obter armas.

### Criando e registrando uma arma

Abaixo, um exemplo de criação de uma espada rara que adiciona efeito de lentidão em uma entidade.
```java
String id = "SLOW_SWORD";

Weapon slowSword = new Weapon(id) {
    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (other instanceof LivingEntity) {
            ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 1));
        }
    }
};

slowSword.setItem(new ItemStack(Material.DIAMOND_SWORD));
slowSword.setName("Espada Lenta");
slowSword.setDescription(Text.translate("&7Aplica um efeito de lentidão", "&7em seus inimigos!"));
slowSword.setType(WeaponType.SWORD);
slowSword.setRarity(WeaponRarity.RARE);
```

### Obtendo o ItemStack

Com o código abaixo, é possível pegar o ItemStack que representará uma arma. O ID informado deve ser de uma já registrada.
```java
Weapons.getWeapons().getWeaponManager().getWeapon("SLOW_SWORD").getItem()
```
