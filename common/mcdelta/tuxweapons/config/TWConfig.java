package mcdelta.tuxweapons.config;

import mcdelta.core.config.ConfigWrapper;
import mcdelta.core.config.IConfig;

public class TWConfig extends IConfig
{
    @Override
    protected void initCommon(ConfigWrapper config)
    {
        config.getConfiguration().addCustomCategoryComment(TWSettings.CATEGORY_GENERAL, TWSettings.COMMENT_GENERAL);
        TWSettings.GREIFING = config.get(TWSettings.CATEGORY_GENERAL, TWSettings.GREIFING_KEY, TWSettings.GREIFING);

        config.getConfiguration().addCustomCategoryComment(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.COMMENT_DAMAGE_MODIFIER);
        TWSettings.DAMAGE_MODIFIER_ENABLE = config.get(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.DAMAGE_MODIFIER_ENABLE_KEY, TWSettings.DAMAGE_MODIFIER_ENABLE);
        TWSettings.DAMAGE_MODIFIER_WEAK = config.get(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.DAMAGE_MODIFIER_WEAK_KEY, TWSettings.DAMAGE_MODIFIER_WEAK);
        TWSettings.DAMAGE_MODIFIER_RESIST = config.get(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.DAMAGE_MODIFIER_RESIST_KEY, TWSettings.DAMAGE_MODIFIER_RESIST);
        TWSettings.DAMAGE_MODIFIER_GOLD = config.get(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.DAMAGE_MODIFIER_GOLD_KEY, TWSettings.DAMAGE_MODIFIER_GOLD);
        TWSettings.DAMAGE_MODIFIER_MACE = config.get(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.DAMAGE_MODIFIER_MACE_KEY, TWSettings.DAMAGE_MODIFIER_MACE);
        TWSettings.DAMAGE_MODIFIER_STRIKE = config.get(TWSettings.CATEGORY_DAMAGE_MODIFIER, TWSettings.DAMAGE_MODIFIER_STRIKE_KEY, TWSettings.DAMAGE_MODIFIER_STRIKE);
    }
}
